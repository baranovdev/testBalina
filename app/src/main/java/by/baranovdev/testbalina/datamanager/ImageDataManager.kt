package by.baranovdev.testbalina.datamanager

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.data.remote.service.ImageApi
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.network.repository.ImageNetworkRepository
import javax.inject.Inject

class ImageDataManager @Inject constructor(
    private val imageNetworkRepository: ImageNetworkRepository,
    private val imageLocalRepository: ImageLocalRepository
) {
    val imageListLocalLiveData = imageLocalRepository.getImageListLiveData()

//    suspend fun loadImageList(callback: (list: List<Image>) -> Unit){
//        val imageList = imageNetworkRepository.loadImageList()
//        imageList.map {
//            imageLocalRepository.insertImage(it)
//        }
//        callback.invoke(imageList)
//    }

}