package by.baranovdev.testbalina.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class NetworkErrorHandler : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return try {
            Log.i("OkHttp", response.toString())
            response
        } catch (e: Exception) {
            Log.e("OkHttp", response.toString())
            response
        }

    }
}