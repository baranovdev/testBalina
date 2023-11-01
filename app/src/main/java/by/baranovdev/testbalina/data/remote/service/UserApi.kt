package by.baranovdev.testbalina.data.remote.service

import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.user.UserRequest
import by.baranovdev.testbalina.data.remote.dto.user.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("api/account/signin")
    suspend fun signIn(
        @Body
        body: UserRequest
    ): Response<UserResponse>

    @POST("api/account/signup")
    suspend fun signUp(
        @Body
        body: UserRequest
    ):  Response<UserResponse>

}