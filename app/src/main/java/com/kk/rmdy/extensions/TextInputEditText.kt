package com.kk.rmdy.extensions

import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
fun TextInputEditText.setTextChangeListener(listener: (Editable?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    })
}

fun TextInputEditText.isDataValid() = !text.isNullOrBlank()