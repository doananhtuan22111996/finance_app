package vn.finance.app.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.finance.app.pages.login.LoginViewModel
import vn.finance.app.pages.main.RootViewModel
import vn.finance.app.utils.networkState.NetworkChange
import vn.finance.app.pages.homeLocal.HomeLocalViewModel
import vn.finance.app.pages.home.HomeViewModel
import vn.finance.app.pages.main.MainViewModel
import vn.finance.app.pages.onBoarding.OnBoardingViewModel
import vn.finance.app.pages.routing.RoutingViewModel
import vn.finance.domain.usecase.SkipOnBoardingUseCase
import vn.finance.domain.usecase.SkippedOnBoardingUseCase

internal object AppModules {
    val applicationModules = module(createdAtStart = true) {
        single { NetworkChange(androidContext()) }
    }

    val viewModelModules = module(createdAtStart = true) {
        viewModel { RoutingViewModel(get<SkippedOnBoardingUseCase>()) }
        viewModel { RootViewModel() }
        viewModel { OnBoardingViewModel(get<SkipOnBoardingUseCase>()) }
        viewModel { LoginViewModel(get(), get()) }
        viewModel { MainViewModel() }
        viewModel { HomeViewModel(get()) }
        viewModel { HomeLocalViewModel(get()) }
    }
}
