package com.kk.rmdy.ui.chartdata

import android.view.View
import com.github.mikephil.charting.components.XAxis
import com.kk.rmdy.R
import com.kk.rmdy.data.remote.error.BaseError
import com.kk.rmdy.data.remote.error.NoConnectivityError
import com.kk.rmdy.data.remote.error.ServerError
import com.kk.rmdy.extensions.getResColor
import com.kk.rmdy.extensions.observe
import com.kk.rmdy.extensions.onClick
import com.kk.rmdy.extensions.setSelectedItemListener
import com.kk.rmdy.ui.base.BaseActivity
import com.kk.rmdy.ui.chartdata.model.ChartViewData
import com.kk.rmdy.util.Range
import com.kk.rmdy.util.chart.YValueFormatter
import kotlinx.android.synthetic.main.activity_chart.*

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
class ChartActivity : BaseActivity<ChartViewModel>(ChartViewModel::class) {

    override fun getLayout() = R.layout.activity_chart

    override fun initUI() {
        getViewModel().apply {
            observe(chartLiveData, ::onDataLoaded)
        }

        btnBack.onClick {
            onBackPressed()
        }
        bottomView.setSelectedItemListener { newRange ->
            loadData(newRange)
        }
        setupChart()
    }

    private fun setupChart() {
        stepsChart.apply {
            axisLeft.axisMinimum = 0f
            axisLeft.valueFormatter = YValueFormatter()
            axisRight.setDrawLabels(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawLabels(true)
            setDrawBorders(true)
            legend.isEnabled = false
            description.isEnabled = false
            setNoDataTextColor(getResColor(R.color.colorPrimary))
        }
    }

    private fun loadData(range: Range) {
        getViewModel().memberTrackerSteps(range)
    }

    private fun onDataLoaded(chartViewData: ChartViewData?) {
        showAxis(chartViewData?.lineData != null)
        stepsChart.apply {
            xAxis.valueFormatter = chartViewData?.formatter
            data = chartViewData?.lineData
            data?.notifyDataChanged()
            notifyDataSetChanged()
            stepsChart.invalidate()
        }
        tvChartHeader.text = getString(R.string.chart_header, chartViewData?.header)
    }

    private fun showAxis(show: Boolean) {
        tvAxisX.visibility = if (show) View.VISIBLE else View.GONE
        tvAxisY.visibility = if (show) View.VISIBLE else View.GONE
        tvChartHeader.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onLoadingStateChanged(isLoading: Boolean?) {
        showAxis(isLoading == false)
        stepsChart.visibility = if (isLoading == true) View.GONE else View.VISIBLE
        progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }

    override fun onNetworkError(error: BaseError?) {
        stepsChart.visibility = View.GONE
        showAxis(false)
        error?.let {
            when (it) {
                is NoConnectivityError -> {
                    showAlert(messageRes = R.string.error_no_connection, onDismiss = ::onBackPressed)
                }
                is ServerError -> {
                    showAlert(it.code.toString(), it.descriptionMessage, onDismiss = ::onBackPressed)
                }
                else -> {
                    showAlert(messageRes = R.string.error_internal, onDismiss = ::onBackPressed)
                }
            }
        } ?: run {
            showAlert(messageRes = R.string.error_internal, onDismiss = ::onBackPressed)
        }
    }

}