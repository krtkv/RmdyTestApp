package com.kk.rmdy.app

import android.app.Application
import com.kk.rmdy.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
class RmdyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RmdyApplication)
            modules(appModule)
        }
    }

}