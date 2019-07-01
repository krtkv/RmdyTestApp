package com.kk.rmdy.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, android.arch.lifecycle.Observer(body))