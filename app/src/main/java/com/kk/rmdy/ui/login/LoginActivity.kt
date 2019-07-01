package com.kk.rmdy.ui.login

import android.annotation.SuppressLint
import android.view.View
import android.view.inputmethod.EditorInfo
import com.kk.rmdy.BuildConfig
import com.kk.rmdy.R
import com.kk.rmdy.data.remote.error.BaseError
import com.kk.rmdy.data.remote.error.NoConnectivityError
import com.kk.rmdy.data.remote.error.ServerError
import com.kk.rmdy.extensions.*
import com.kk.rmdy.ui.base.BaseActivity
import com.kk.rmdy.ui.chartdata.ChartActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {

    override fun getLayout() = R.layout.activity_login

    @SuppressLint("SetTextI18n")
    override fun initUI() {

        getViewModel().apply {
            observe(loginLiveData, ::onMemberLogin)
        }

        etUsername.setTextChangeListener {
            layUsername.error =
                if (!etUsername.isDataValid()) getString(R.string.error_field_empty, layUsername.hint) else null
        }
        etPassword.setTextChangeListener {
            layPassword.error =
                if (!etPassword.isDataValid()) getString(R.string.error_field_empty, layPassword.hint) else null
        }
        etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                startLoginFlow()
            else
                false
        }
        btnLogin.onClick {
            startLoginFlow()
        }

        if (BuildConfig.DEBUG) {
            etUsername.setText("Augustus")
            etPassword.setText("5E065CDCC904FA398B8B0DB3AA268E9F")

            etUsername.clearFocus()
            etPassword.clearFocus()
        }

    }

    private fun startLoginFlow(): Boolean {
        if (!etUsername.isDataValid()) {
            layUsername.error = getString(R.string.error_field_empty, layUsername.hint)
            return true
        }
        if (!etPassword.isDataValid()) {
            layPassword.error = getString(R.string.error_field_empty, layPassword.hint)
            return true
        }

        hideKeyboard()

        getViewModel().memberLogin(etUsername.text.toString(), etPassword.text.toString())
        return false
    }

    private fun onMemberLogin(isLoggedIn: Boolean?) {
        if (isLoggedIn == true) launchActivity<ChartActivity> { }
    }

    override fun onLoadingStateChanged(isLoading: Boolean?) {

        progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
        etUsername.isEnabled = isLoading == false
        etPassword.isEnabled = isLoading == false
        btnLogin.isEnabled = isLoading == false
    }

    override fun onNetworkError(error: BaseError?) {
        error?.let {
            when (it) {
                is NoConnectivityError -> {
                    showAlert(messageRes = R.string.error_no_connection)
                }
                is ServerError -> {
                    showAlert(it.code.toString(), it.descriptionMessage)
                }
                else -> {
                    showAlert(messageRes = R.string.error_internal)
                }
            }
        } ?: run {
            showAlert(messageRes = R.string.error_internal)
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}