package by.baranovdev.testbalina.ui.imagedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.database.repository.CommentLocalRepository
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.network.pagingsource.CommentPagingSource
import by.baranovdev.testbalina.network.pagingsource.ImagePagingSource
import by.baranovdev.testbalina.network.repository.CommentNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val commentLocalRepository: CommentLocalRepository,
    private val commentNetworkRepository: CommentNetworkRepository,
    private val imageLocalRepository: ImageLocalRepository
) : ViewModel() {

    private var currentPagingSource: CommentPagingSource? = null

    private var _commentFlow: StateFlow<PagingData<Comment>>? = null

    private var imageId: Int? = null

    private val _imageUrl: MutableLiveData<String> = MutableLiveData()
    val imageUrl: LiveData<String> = _imageUrl

    fun setImageId(imageId: Int) {
        this.imageId = imageId
        _commentFlow = Pager(ImagePagingSource.DEFAULT_PAGING_CONFIG) {
            CommentPagingSource(
                commentNetworkRepository = commentNetworkRepository,
                commentLocalRepository = commentLocalRepository,
                imageId = imageId
            ).apply {
                currentPagingSource = this
            }
        }.flow.cachedIn(CoroutineScope(Dispatchers.IO))
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        viewModelScope.launch {
            val url = imageLocalRepository.getImageById(imageId).url
            url?.let { _imageUrl.postValue(it) }
        }
    }

    fun loadCommentFlow(): StateFlow<PagingData<Comment>>? {
        return _commentFlow
    }

    fun sendComment(text: String) {
        imageId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                commentNetworkRepository.sendComment(
                    text = text,
                    imageId = it
                )
            }
            refreshComments()
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            commentNetworkRepository.deleteComment(comment)
        }
        refreshComments()
    }

    private fun refreshComments() {
        currentPagingSource?.invalidate()
    }
}