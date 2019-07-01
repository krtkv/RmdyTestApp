package com.kk.rmdy.ui.chartdata

import android.arch.lifecycle.MutableLiveData
import com.kk.rmdy.data.remote.Result
import com.kk.rmdy.ui.base.BaseViewModel
import com.kk.rmdy.ui.chartdata.model.ChartViewData
import com.kk.rmdy.util.Range
import com.kk.rmdy.util.resourse.ResourceProvider
import kotlinx.coroutines.*
import org.koin.core.inject

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
class ChartViewModel : BaseViewModel() {

    var chartLiveData = MutableLiveData<ChartViewData>()

    private val resourceProvider: ResourceProvider by inject()

    private var range = Range.Week

    init {
        memberTrackerSteps(range)
    }

    fun memberTrackerSteps(range: Range) {
        isLoading.postValue(true)
        coroutineContext.cancelChildren()
        launch {
            val result = networkRepository.memberTrackerSteps(range.name)
            when (result) {
                is Result.Success -> {
                    isLoading.postValue(false)
                    chartLiveData.postValue(ChartViewData.transformFromResponse(result.data, resourceProvider))
                }
                is Result.Error -> {
                    isLoading.postValue(false)
                    networkError.postValue(result.networkError)
                }
            }
        }
    }


}