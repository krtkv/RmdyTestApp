package com.kk.rmdy.extensions

import android.view.View

/**
 * Created by Kirill Kartukov on 28.06.2019.
 */
fun View.onClick(code: (View?) -> Unit) {

    setOnClickListener(object : View.OnClickListener {

        private val MIN_CLICK_INTERVAL: Long = 300
        private var mLastClickTime: Long = 0

        fun onSingleClick(v: View?) {
            code.invoke(v)
        }

        override fun onClick(v: View?) {
            val currentClickTime = System.currentTimeMillis()
            if ((currentClickTime - mLastClickTime) >= MIN_CLICK_INTERVAL) {
                mLastClickTime = currentClickTime
                onSingleClick(v)
            } else {
                return
            }
        }
    })
}