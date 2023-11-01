package by.baranovdev.testbalina.database.repository

import androidx.lifecycle.LiveData
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.data.model.data.dao.ImageDao
import javax.inject.Inject

class ImageLocalRepository @Inject constructor(
    private val imageDao: ImageDao
) {

    fun getImageListLiveData(): LiveData<List<Image>> = imageDao.getImages()

    fun getImageList(): List<Image> = imageDao.getImages().value.orEmpty()

    suspend fun getImagePage(page: Int, size: Int) = imageDao.getImagePage(page,size)

    fun insertImage(image: Image) = imageDao.insertImage(image)

    suspend fun getImageById(imageId: Int): Image = imageDao.getImageById(imageId)

    fun deleteImage(image: Image) = imageDao.deleteImage(image)

}