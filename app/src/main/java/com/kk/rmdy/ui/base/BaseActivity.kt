package com.kk.rmdy.ui.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.kk.rmdy.R
import com.kk.rmdy.data.remote.error.BaseError
import com.kk.rmdy.extensions.observe
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
abstract class BaseActivity<out ViewModelType : BaseViewModel>(clazz: KClass<ViewModelType>) : AppCompatActivity() {

    private val vm: ViewModelType by viewModel(clazz)
    private var alertDialog: AlertDialog? = null

    abstract fun getLayout(): Int

    abstract fun initUI()

    abstract fun onLoadingStateChanged(isLoading: Boolean?)

    abstract fun onNetworkError(error: BaseError?)

    fun getViewModel() = vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initUI()
        getViewModel().apply {
            observe(isLoading, ::onLoadingStateChanged)
            observe(networkError, ::onNetworkError)
        }

    }

    protected fun showAlert(title: String? = null, @StringRes messageRes: Int, onDismiss: (() -> Unit)? = null) {
        alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(messageRes)
            .setPositiveButton(R.string.btn_close) { dialogInterface, _ ->
                dialogInterface.dismiss()
                onDismiss?.invoke()
            }
            .setCancelable(onDismiss == null)
            .create()
        if (!isFinishing && alertDialog?.isShowing == false) alertDialog?.show()

        baseContext.resources
    }

    protected fun showAlert(title: String? = null, messageString: String?, onDismiss: (() -> Unit)? = null) {
        alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(messageString)
            .setPositiveButton(R.string.btn_close) { dialogInterface, _ ->
                dialogInterface.dismiss()
                onDismiss?.invoke()
            }
            .setCancelable(onDismiss == null)
            .create()
        if (!isFinishing && alertDialog?.isShowing == false) alertDialog?.show()

        baseContext.resources
    }

}