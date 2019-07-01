package com.kk.rmdy.util.chart

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.NumberFormat
import java.util.*

/**
 * Created by Kirill Kartukov on 28.06.2019.
 */
class YValueFormatter : ValueFormatter() {

    private val formatter = NumberFormat.getNumberInstance(Locale.US)

    override fun getFormattedValue(value: Float): String {
        if (value % value.toInt() != 0f)
            return ""

        return formatter.format(value.toInt())
    }

}