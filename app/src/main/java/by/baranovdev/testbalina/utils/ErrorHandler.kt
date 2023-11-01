package by.baranovdev.testbalina.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.base.NetworkError
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.base.Status
import javax.inject.Inject

class ErrorHandler @Inject constructor() {

    private val _networkErrorLiveData: MutableLiveData<NetworkError> = MutableLiveData()
    val networkErrorLiveData: LiveData<NetworkError> = _networkErrorLiveData

    fun initError(error: NetworkError){
        _networkErrorLiveData.value = error
    }

}


fun <T> Result<Response<T>>.parseResult(errorHandler: ErrorHandler): Response<T> {
    onFailure {
        Log.e("NETWORK ERROR", it.message.toString())
        errorHandler.initError(NetworkError(it.message.toString()))
        return Response(data = null, status = Status.ERROR)
    }
    return Response(data = this.getOrNull()?.data, Status.OK)
}

fun <T> Result<ListResponse<T>>.parseResult(errorHandler: ErrorHandler): ListResponse<T> {
    onFailure {
        Log.e("NETWORK ERROR", it.message.toString())
        errorHandler.initError(NetworkError(it.message.toString()))
        return ListResponse(data = null, status = Status.ERROR)
    }
    return ListResponse(data = this.getOrNull()?.data, Status.OK)
}
