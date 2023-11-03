package by.baranovdev.testbalina.data.remote.dto.base

import android.util.Log
import com.google.gson.annotations.SerializedName

class ListResponse<R>(
    @field:SerializedName("data")
    val data: List<R>?,
    @field:SerializedName("status")
    val status: Int?
)

