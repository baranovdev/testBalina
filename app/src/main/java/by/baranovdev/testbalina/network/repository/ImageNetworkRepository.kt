package by.baranovdev.testbalina.network.repository

import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.data.model.mapper.ImageListMapper
import by.baranovdev.testbalina.data.model.mapper.ImageMapper
import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.image.ImageRequest
import by.baranovdev.testbalina.data.remote.dto.image.ImageResponse
import by.baranovdev.testbalina.data.remote.service.ImageApi
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.database.repository.UserLocalRepository
import by.baranovdev.testbalina.utils.BaseUtils.ifFalse
import by.baranovdev.testbalina.utils.BaseUtils.ifTrue
import by.baranovdev.testbalina.utils.ErrorHandler
import by.baranovdev.testbalina.utils.getDefaultCallback
import by.baranovdev.testbalina.utils.getDefaultListCallback
import by.baranovdev.testbalina.utils.parseResult
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ImageNetworkRepository @Inject constructor(
    private val imageApi: ImageApi,
    private val imageListMapper: ImageListMapper,
    private val imageMapper: ImageMapper,
    private val userLocalRepository: UserLocalRepository,
    private val imageLocalRepository: ImageLocalRepository,
    private val errorHandler: ErrorHandler
) {

    suspend fun loadImageList(page: Int): List<Image> {
        val response = imageApi.loadImages(
            page = page,
            token = userLocalRepository.getUser()?.token)
        return imageListMapper.mapFromNetworkToUi(
            response.parseResult(errorHandler)
        )
    }

    suspend fun sendImage(imageBase64: String, lat: Double, lng: Double): Image {
        val response = imageApi.sendImage(
            token = userLocalRepository.getUser()?.token,
            requestBody = ImageRequest(
                date = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toInt(),
                lng = lng,
                lat = lat,
                base64Image = imageBase64,
            )
        )
        return imageMapper.mapFromNetworkToUi(response.parseResult(errorHandler))
    }

    fun deleteImage(imageId: Int) {
        imageApi.deleteImage(token = userLocalRepository.getUser()?.token, imageId = imageId)
    }
}