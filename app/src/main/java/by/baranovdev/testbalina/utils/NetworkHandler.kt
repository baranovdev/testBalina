package by.baranovdev.testbalina.utils

import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import retrofit2.Call
import retrofit2.Callback
import by.baranovdev.testbalina.data.remote.dto.base.Response

fun <T> getDefaultCallback(f: (Response<T>?) -> Unit):Callback<Response<T>> = object: Callback<Response<T>>{
    override fun onResponse(call: Call<Response<T>>, response: retrofit2.Response<Response<T>>) {
        f.invoke(response.body())
    }

    override fun onFailure(call: Call<Response<T>>, t: Throwable) {
        f.invoke(null)
    }

}

fun <T> getDefaultListCallback(f: (ListResponse<T>?) -> Unit):Callback<ListResponse<T>> = object: Callback<ListResponse<T>>{
    override fun onResponse(call: Call<ListResponse<T>>, response: retrofit2.Response<ListResponse<T>>) {
        f.invoke(response.body())
    }

    override fun onFailure(call: Call<ListResponse<T>>, t: Throwable) {
        f.invoke(null)
    }

}