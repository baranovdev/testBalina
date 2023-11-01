package by.baranovdev.testbalina.data.remote.service

import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.image.ImageRequest
import by.baranovdev.testbalina.data.remote.dto.image.ImageResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {

    @GET("api/image")
    suspend fun loadImages(
        @Header("Access-Token")
        token: String?,
        @Query("page")
        page: Int = 0
    ): ListResponse<ImageResponse>

    @POST("api/image")
    suspend fun sendImage(
        @Header("Access-Token")
        token: String?,
        @Body
        requestBody: ImageRequest
    ):Response<ImageResponse>

    @DELETE("api/image/{imageId}")
    suspend fun deleteImage(
        @Header("Access-Token")
        token: String?,
        @Path("imageId")
        imageId: Int,
    ): Response<ImageResponse>

}