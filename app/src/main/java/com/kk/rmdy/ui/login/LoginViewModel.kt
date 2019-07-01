package com.kk.rmdy.ui.login

import android.arch.lifecycle.MutableLiveData
import com.kk.rmdy.data.remote.Result
import com.kk.rmdy.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
class LoginViewModel : BaseViewModel() {

    val loginLiveData = MutableLiveData<Boolean>()

    fun memberLogin(userName: String, userPass: String) {
        isLoading.postValue(true)
        launch {
            val result = networkRepository.memberLogin(userName, userPass)
            when (result) {
                is Result.Success -> {
                    prefsRepository.saveTokens(result.data.token?.authToken, result.data.token?.sessionToken)
                    isLoading.postValue(false)
                    loginLiveData.postValue(true)
                }
                is Result.Error -> {
                    isLoading.postValue(false)
                    networkError.postValue(result.networkError)
                }
            }
        }
    }

}