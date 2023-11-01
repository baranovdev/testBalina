package by.baranovdev.testbalina.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.baranovdev.testbalina.database.repository.UserLocalRepository
import by.baranovdev.testbalina.database.sharedpref.UserPreferences
import by.baranovdev.testbalina.network.repository.UserNetworkRepository
import by.baranovdev.testbalina.utils.BaseUtils.doIfNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFlowViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userLocalRepository: UserLocalRepository,
    private val userNetworkRepository: UserNetworkRepository
): ViewModel() {

    private val _loginFlowStateLiveData: MutableLiveData<LoginFlowState> = MutableLiveData()
    val loginFlowStateLiveData: LiveData<LoginFlowState> = _loginFlowStateLiveData

    fun signIn(login: String, password: String){
        viewModelScope.launch(Dispatchers.IO) { userNetworkRepository.signIn(login, password) }
    }

    fun signUp(login: String, password: String){
        viewModelScope.launch(Dispatchers.IO) { userNetworkRepository.signUp(login, password) }
    }

    enum class LoginFlowState{
        LOG_IN, SIGN_UP
    }
}

