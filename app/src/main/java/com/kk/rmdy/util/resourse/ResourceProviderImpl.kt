package com.kk.rmdy.util.resourse

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * Created by Kirill Kartukov on 01.07.2019.
 */
class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getColor(color: Int) = ContextCompat.getColor(context, color)

}