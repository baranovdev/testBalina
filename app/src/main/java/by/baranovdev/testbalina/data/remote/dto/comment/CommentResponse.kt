package by.baranovdev.testbalina.data.remote.dto.comment

import com.google.gson.annotations.SerializedName

data class CommentResponse(

	@field:SerializedName("date")
	val date: Int? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
