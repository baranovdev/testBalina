package by.baranovdev.testbalina.data.remote.dto.comment

import com.google.gson.annotations.SerializedName

data class CommentRequest(
	@field:SerializedName("text")
	val text: String? = null
)
