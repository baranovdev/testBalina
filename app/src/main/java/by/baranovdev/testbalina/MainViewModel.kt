package by.baranovdev.testbalina

import androidx.lifecycle.ViewModel
import by.baranovdev.testbalina.network.interceptor.NetworkErrorHandler
import by.baranovdev.testbalina.utils.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val errorHandler: ErrorHandler
): ViewModel() {

    val networkErrorLiveData = errorHandler.networkErrorLiveData

}