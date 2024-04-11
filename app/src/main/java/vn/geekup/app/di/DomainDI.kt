package vn.geekup.app.di

import org.koin.dsl.module
import vn.geekup.domain.usecase.LoginUseCase
import vn.geekup.domain.usecase.LoginUseCaseImpl
import vn.geekup.domain.usecase.LogoutUseCase
import vn.geekup.domain.usecase.LogoutUseCaseImpl
import vn.geekup.domain.usecase.PagingLocalUseCase
import vn.geekup.domain.usecase.PagingLocalUseCaseImpl
import vn.geekup.domain.usecase.PagingNetworkUseCase
import vn.geekup.domain.usecase.PagingNetworkUseCaseImpl

internal object DomainModules {
    val useCaseModules = module(createdAtStart = true) {
        factory<LoginUseCase> { LoginUseCaseImpl(get()) }
        factory<LogoutUseCase> { LogoutUseCaseImpl(get()) }
        factory<PagingNetworkUseCase> { PagingNetworkUseCaseImpl(get()) }
        factory<PagingLocalUseCase> { PagingLocalUseCaseImpl(get()) }
    }
}
