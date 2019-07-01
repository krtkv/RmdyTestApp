package com.kk.rmdy.ui.chartdata.model

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kk.rmdy.R
import com.kk.rmdy.data.remote.response.trackersteps.MemberTrackerStepsResponse
import com.kk.rmdy.util.DateFormatter
import com.kk.rmdy.util.resourse.ResourceProvider
import com.kk.rmdy.util.chart.XValueFormatter
import kotlin.collections.ArrayList

/**
 * Created by Kirill Kartukov on 28.06.2019.
 */
data class ChartViewData(val header: String, val lineData: LineData?, val formatter: XValueFormatter) {

    companion object {

        fun transformFromResponse(
            response: MemberTrackerStepsResponse,
            resourceProvider: ResourceProvider
        ): ChartViewData {

            val mainColors = arrayListOf(resourceProvider.getColor(R.color.colorBlue))
            val headerBuilder = StringBuilder()
            val data = ArrayList<Entry>()

            for ((i, graphData) in response.graphData.withIndex()) {
                data.add(Entry(i.toFloat(), graphData.steps))
            }
            val dataSet = LineDataSet(data, null).apply {
                circleColors = mainColors
                colors = mainColors
                circleHoleColor = resourceProvider.getColor(R.color.colorOrange)
                lineWidth = 2f
                circleRadius = 5f
                circleHoleRadius = 3f
                setDrawValues(false)
            }
            if (response.graphData.size > 0) {
                val startDate = response.graphData[0].startDate
                val endDate = response.graphData[response.graphData.size - 1].startDate
                if (DateFormatter.isSameDate(startDate, endDate)) {
                    headerBuilder.append(DateFormatter.formatDate(startDate))
                } else {
                    headerBuilder.append(DateFormatter.formatDate(startDate))
                    headerBuilder.append(" - ")
                    headerBuilder.append(DateFormatter.formatDate(endDate))
                }
            }

            return ChartViewData(
                headerBuilder.toString(),
                if (data.size > 0) LineData(dataSet) else null,
                XValueFormatter(response.graphData)
            )
        }
    }

}