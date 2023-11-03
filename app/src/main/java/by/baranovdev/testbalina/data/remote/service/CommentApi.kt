package by.baranovdev.testbalina.data.remote.service

import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.comment.CommentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {

    @POST("api/image/{imageId}/comment")
    suspend fun sendComment(
        @Header("Access-Token")
        token: String?,
        @Path("imageId")
        imageId: Int,
        @Body
        commentText: String
    ): retrofit2.Response<Response<CommentResponse>>

    @GET("api/image/{imageId}/comment")
    suspend fun getComments(

        @Header("Access-Token")
        token: String?,

        @Path(value = "imageId") imageId: Int,

        @Query("page")
        page: Int
    ): retrofit2.Response<ListResponse<CommentResponse>>

    @DELETE("api/image/{imageId}/comment/{commentId}")
    suspend fun deleteComment(
        @Header("Access-Token")
        token: String?,
        @Path("imageId")
        imageId: Int?,
        @Path("commentId")
        commentId: Int?
    )

}