package by.baranovdev.testbalina.data.model.mapper

import by.baranovdev.testbalina.BuildConfig
import by.baranovdev.testbalina.data.model.data.User
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.user.UserResponse

class UserMapper : Mapper<User, UserResponse> {
    override fun mapFromNetworkToUi(response: Response<UserResponse>): User =
        response.let{
            User(
                userId = it.data?.userId,
                login = it.data?.login,
                token = it.data?.token
            )
        }
}