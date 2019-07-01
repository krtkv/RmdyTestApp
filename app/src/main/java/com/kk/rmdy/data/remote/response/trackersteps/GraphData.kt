package com.kk.rmdy.data.remote.response.trackersteps

import com.google.gson.annotations.SerializedName

/**
 * Created by Kirill Kartukov on 28.06.2019.
 */
data class GraphData(
    @SerializedName("StartDate") val startDate: String = "",
    @SerializedName("Steps") val steps: Float = 0f
)