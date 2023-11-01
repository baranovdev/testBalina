package by.baranovdev.testbalina.data.remote.dto.image

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @field:SerializedName("date")
    val date: Int? = null,

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)
