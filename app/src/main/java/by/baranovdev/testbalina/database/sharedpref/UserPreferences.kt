package by.baranovdev.testbalina.database.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import by.baranovdev.testbalina.data.model.data.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private fun getPreferences(): SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setUser(user: User) = getPreferences().edit().putString(USER_DATA_PREFERENCES_NAME, gson.toJson(user))

    fun getUser(): User? = gson.fromJson(
        getPreferences().getString(USER_DATA_PREFERENCES_NAME, null),
        User::class.java
    )


    companion object{
        private const val PREFERENCES_NAME = "app.preferences"
        private const val USER_DATA_PREFERENCES_NAME = "app.preferences.user_data"
    }
}