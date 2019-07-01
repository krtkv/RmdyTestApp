package com.kk.rmdy.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kirill Kartukov on 28.06.2019.
 */
object DateFormatter {

    @SuppressLint("SimpleDateFormat")
    private val inFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @SuppressLint("SimpleDateFormat")
    private val outFormat = SimpleDateFormat("MMM dd", Locale.US)
    @SuppressLint("SimpleDateFormat")
    private val compareFormat = SimpleDateFormat("yyyyMMdd", Locale.US)

    fun formatDate(startDate: String): String {
        return try {
            outFormat.format(inFormat.parse(startDate)) ?: ""
        } catch (e: ParseException) {
            ""
        }
    }

    fun isSameDate(left: String, right: String): Boolean {
        return try {
            compareFormat.format(inFormat.parse(left)) == compareFormat.format(
                inFormat.parse(right)
            )
        } catch (e: ParseException) {
            false
        }
    }

}