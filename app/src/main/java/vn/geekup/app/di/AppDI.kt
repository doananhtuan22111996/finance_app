package vn.geekup.app.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.geekup.app.pages.login.LoginViewModel
import vn.geekup.app.pages.root.RootViewModel
import vn.geekup.app.network.NetworkChange
import vn.geekup.app.pages.home.HomeViewModel
import vn.geekup.app.pages.main.MainViewModel

internal object AppModules {
    val applicationModules = module(createdAtStart = true) {
        single { NetworkChange(androidContext()) }
    }

    val viewModelModules = module(createdAtStart = true) {
        viewModel { RootViewModel() }
        viewModel { LoginViewModel(get(), get()) }
        viewModel { MainViewModel() }
        viewModel { HomeViewModel(get()) }
    }
}
