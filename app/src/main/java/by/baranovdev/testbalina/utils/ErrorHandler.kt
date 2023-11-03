package by.baranovdev.testbalina.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.base.NetworkError
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.base.Status
import by.baranovdev.testbalina.data.remote.dto.image.ImageResponse
import by.baranovdev.testbalina.utils.BaseUtils.ifFalse
import by.baranovdev.testbalina.utils.BaseUtils.ifTrue
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler {

    private val _networkErrorLiveData: MutableLiveData<NetworkError> = MutableLiveData()
    val networkErrorLiveData: LiveData<NetworkError> = _networkErrorLiveData

    fun initError(error: NetworkError) {
        _networkErrorLiveData.postValue(error)
    }

}


fun <T> retrofit2.Response<Response<T>>.parseResult(errorHandler: ErrorHandler): Response<T> {
    this.isSuccessful.ifTrue {
        when (this.body()?.status?.toStatus()) {
            Status.ERROR_UNKNOWN -> errorHandler.initError(NetworkError(message = "Unknown error"))
            Status.ERROR_NOT_FOUND -> errorHandler.initError(NetworkError(message = "Not found"))
            Status.ERROR_UNAUTHORIZED -> errorHandler.initError(NetworkError(message = "You should be authorized"))
            Status.ERROR_NO_ACCESS -> errorHandler.initError(NetworkError(message = "Token expired"))
            else -> {}
        }
    }
    val body = this.body()
    return if (this.isSuccessful && body != null) body else Response(data = null, status = 400)
}

fun <T> retrofit2.Response<ListResponse<T>>.parseResult(errorHandler: ErrorHandler): ListResponse<T> {
    when (this.body()?.status?.toStatus()) {
        Status.ERROR_UNKNOWN -> errorHandler.initError(NetworkError(message = "Unknown error"))
        Status.ERROR_NOT_FOUND -> errorHandler.initError(NetworkError(message = "Not found"))
        Status.ERROR_UNAUTHORIZED -> errorHandler.initError(NetworkError(message = "You should be authorized"))
        Status.ERROR_NO_ACCESS -> errorHandler.initError(NetworkError(message = "Token expired"))
        else -> {}
    }
    val body = this.body()
    return if (this.isSuccessful && body != null) body else ListResponse(data = null, status = 400)
}

fun Int.toStatus(): Status = when (this) {
    200 -> Status.OK
    400 -> Status.ERROR_UNKNOWN
    401 -> Status.ERROR_UNAUTHORIZED
    403 -> Status.ERROR_NO_ACCESS
    404 -> Status.ERROR_NOT_FOUND
    else -> Status.ERROR_UNKNOWN
}

