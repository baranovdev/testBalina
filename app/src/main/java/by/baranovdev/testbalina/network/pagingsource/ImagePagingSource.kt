package by.baranovdev.testbalina.network.pagingsource

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.util.query
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.network.repository.ImageNetworkRepository
import javax.inject.Inject

class ImagePagingSource(
    private val imageNetworkRepository: ImageNetworkRepository,
    private val imageLocalRepository: ImageLocalRepository
): PagingSource<Int, Image>() {

    private var loadingFlag = ImageSource.NETWORK

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page: Int = params.key ?: 0
        val pageSize = params.loadSize

        val response = imageNetworkRepository.loadImageList(page)
        response?.map { imageLocalRepository.insertImage(it) }
        val data = response?.let{
            loadingFlag = ImageSource.NETWORK
            it
        } ?: imageLocalRepository.getImagePage(page, pageSize)

        val nextKey = if(data.size < pageSize) null else page + 1
        val prevKey = if(page == 0) null else page - 1
        return LoadResult.Page(data, prevKey, nextKey)
    }

    enum class ImageSource{
        LOCAL, NETWORK
    }

    fun setLoadingFlag(flag: ImageSource){
        loadingFlag = flag
    }

    companion object{
        val DEFAULT_PAGING_CONFIG = PagingConfig(initialLoadSize = 20, pageSize = 20)
    }
}