package by.baranovdev.testbalina.data.remote.dto.base

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Response<R>(
    @field:SerializedName("data")
    val data: R?,
    @field:SerializedName("status")
    val status: Status?
)

