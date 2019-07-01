package com.kk.rmdy.data.remote.response.trackersteps

import com.google.gson.annotations.SerializedName

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
data class MemberTrackerStepsResponse(
    @SerializedName("GraphData") val graphData: ArrayList<GraphData> = ArrayList(),
    @SerializedName("Goal") val goal: Int = 0
)