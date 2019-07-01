package com.kk.rmdy.extensions

import android.support.design.widget.BottomNavigationView
import com.kk.rmdy.R
import com.kk.rmdy.util.Range

/**
 * Created by Kirill Kartukov on 28.06.2019.
 */
fun BottomNavigationView.setSelectedItemListener(listener: (Range) -> Unit) {
    setOnNavigationItemSelectedListener {
        if (it.itemId == selectedItemId)
            return@setOnNavigationItemSelectedListener true
        when (it.itemId) {
            R.id.action_week -> {
                listener.invoke(Range.Week)
            }
            R.id.action_month -> {
                listener.invoke(Range.Month)
            }
            R.id.action_quarter -> {
                listener.invoke(Range.Quarter)
            }
        }
        true
    }
}