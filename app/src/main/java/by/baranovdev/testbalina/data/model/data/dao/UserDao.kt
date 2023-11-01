package by.baranovdev.testbalina.data.model.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.baranovdev.testbalina.data.model.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getUserLiveData(): LiveData<User>

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): User?

    @Insert
    fun setUser(user: User)

    @Query("DELETE FROM user_table")
    fun clearUser()

    @Update
    fun updateUser(user: User)
}