package by.baranovdev.testbalina.network.repository

import android.util.Log
import by.baranovdev.testbalina.data.model.mapper.UserMapper
import by.baranovdev.testbalina.data.remote.dto.user.UserRequest
import by.baranovdev.testbalina.data.remote.service.UserApi
import by.baranovdev.testbalina.database.repository.UserLocalRepository
import by.baranovdev.testbalina.utils.ErrorHandler
import by.baranovdev.testbalina.utils.parseResult
import javax.inject.Inject

class UserNetworkRepository @Inject constructor(
    private val userApi: UserApi,
    private val userLocalRepository: UserLocalRepository,
    private val errorHandler: ErrorHandler
) {
    suspend fun signIn(login: String, password: String){
        Log.e("User Request", UserRequest(login, password).toString())
        val userMapper = UserMapper()
        val userResponse = userApi.signIn(
            UserRequest(login, password)
        )
//            .parseResult(errorHandler)
        userLocalRepository.setUser(
            userMapper.mapFromNetworkToUi(userResponse)
        )
    }

    suspend fun signUp(login: String, password: String) = let {
        Log.e("User Request", UserRequest(login, password).toString())
        val userMapper = UserMapper()
        val userResponse = userApi.signUp(
            UserRequest(login, password)
        )
        userLocalRepository.setUser(
            userMapper.mapFromNetworkToUi(userResponse)
        )
    }
}