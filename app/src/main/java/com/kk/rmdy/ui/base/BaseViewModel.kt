package com.kk.rmdy.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kk.rmdy.data.local.PreferenceRepository
import com.kk.rmdy.data.remote.NetworkRepository
import com.kk.rmdy.data.remote.error.BaseError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent {

    private val job = SupervisorJob()

    override val coroutineContext = Dispatchers.Main + job

    protected val networkRepository: NetworkRepository by inject()

    protected val prefsRepository: PreferenceRepository by inject()

    val isLoading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<BaseError>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}