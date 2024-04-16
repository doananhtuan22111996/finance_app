package vn.finance.app.di

import org.koin.dsl.module
import vn.finance.domain.usecase.LoginUseCase
import vn.finance.domain.usecase.LoginUseCaseImpl
import vn.finance.domain.usecase.LogoutUseCase
import vn.finance.domain.usecase.LogoutUseCaseImpl
import vn.finance.domain.usecase.PagingLocalUseCase
import vn.finance.domain.usecase.PagingLocalUseCaseImpl
import vn.finance.domain.usecase.PagingNetworkUseCase
import vn.finance.domain.usecase.PagingNetworkUseCaseImpl
import vn.finance.domain.usecase.SkipOnBoardingUseCase
import vn.finance.domain.usecase.SkippedOnBoardingUseCase

internal object DomainModules {
    val useCaseModules = module(createdAtStart = true) {
        factory<SkipOnBoardingUseCase> { SkipOnBoardingUseCase(get()) }
        factory<SkippedOnBoardingUseCase> { SkippedOnBoardingUseCase(get()) }
        factory<LoginUseCase> { LoginUseCaseImpl(get()) }
        factory<LogoutUseCase> { LogoutUseCaseImpl(get()) }
        factory<PagingNetworkUseCase> { PagingNetworkUseCaseImpl(get()) }
        factory<PagingLocalUseCase> { PagingLocalUseCaseImpl(get()) }
    }
}
