package by.baranovdev.testbalina.data.remote.dto.user

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class UserRequest(
	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("password")
	val password: String? = null,
)
