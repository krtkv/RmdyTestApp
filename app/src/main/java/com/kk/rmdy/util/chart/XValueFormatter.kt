package com.kk.rmdy.util.chart

import com.github.mikephil.charting.formatter.ValueFormatter
import com.kk.rmdy.data.remote.response.trackersteps.GraphData
import com.kk.rmdy.util.DateFormatter
import kotlin.collections.ArrayList

/**
 * Created by Kirill Kartukov on 28.06.2019.
 */
class XValueFormatter(val data: ArrayList<GraphData>) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {

        if (data.size > 1 && value % value.toInt() != 0f)
            return ""
        else if (data.size == 1) {
            return if (value == 0f)
                data.getOrNull(value.toInt())?.let {
                    DateFormatter.formatDate(it.startDate)
                } ?: run {
                    ""
                }
            else
                ""
        }
        return try {
            data.getOrNull(value.toInt())?.let {
                DateFormatter.formatDate(it.startDate)
            } ?: run {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }


}