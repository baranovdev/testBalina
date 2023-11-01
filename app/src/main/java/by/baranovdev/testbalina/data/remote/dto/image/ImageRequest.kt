package by.baranovdev.testbalina.data.remote.dto.image

import com.google.gson.annotations.SerializedName

data class ImageRequest(

	@field:SerializedName("date")
	val date: Int? = null,

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("base64Image")
	val base64Image: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)
