package by.baranovdev.testbalina.database.repository

import androidx.lifecycle.LiveData
import by.baranovdev.testbalina.data.model.data.User
import by.baranovdev.testbalina.data.model.data.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalRepository @Inject constructor(
    private val userDao: UserDao
){
    fun getUserLiveData(): LiveData<User> = userDao.getUserLiveData()

    fun getUser(): User? = userDao.getUser()

    fun setUser(user: User) {
        userDao.clearUser()
        userDao.setUser(user)
    }
}