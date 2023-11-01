package by.baranovdev.testbalina.datamanager

import by.baranovdev.testbalina.database.repository.UserLocalRepository
import by.baranovdev.testbalina.network.repository.UserNetworkRepository
import javax.inject.Inject

class UserDataManager @Inject constructor(
    private val userLocalRepository: UserLocalRepository,
    private val userNetworkRepository: UserNetworkRepository
) {
//    fun signIn(login: String, password: String){
//        userNetworkRepository.signIn(login,password)
//    }
//    fun signUp(login: String, password: String){
//        userNetworkRepository.signUp(login,password)
//    }
}