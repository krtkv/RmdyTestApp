package com.kk.rmdy.di

import android.content.Context
import com.google.gson.Gson
import com.kk.rmdy.data.local.PreferenceRepository
import com.kk.rmdy.data.local.PreferenceRepositoryImpl
import com.kk.rmdy.data.remote.NetworkRepository
import com.kk.rmdy.data.remote.NetworkRepositoryImpl
import com.kk.rmdy.data.remote.NetworkService
import com.kk.rmdy.data.remote.NetworkServiceImpl
import com.kk.rmdy.ui.chartdata.ChartViewModel
import com.kk.rmdy.ui.login.LoginViewModel
import com.kk.rmdy.util.resourse.ResourceProvider
import com.kk.rmdy.util.resourse.ResourceProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
val appModule = module {

    single<NetworkService> { NetworkServiceImpl() }
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }
    single<PreferenceRepository> {
        PreferenceRepositoryImpl(
            androidContext().getSharedPreferences(
                PreferenceRepository.PrefKeys.RmdyPrefs.key,
                Context.MODE_PRIVATE
            )
        )
    }
    single<NetworkRepository> {
        NetworkRepositoryImpl(
            androidContext(),
            get(),
            get<NetworkService>().getMtdService(),
            Gson()
        )
    }

    viewModel { LoginViewModel() }
    viewModel { ChartViewModel() }

}