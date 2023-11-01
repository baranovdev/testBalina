package by.baranovdev.testbalina.data.remote.dto.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)
