package vn.geekup.app.di

import org.koin.dsl.module
import vn.geekup.domain.usecase.LoginUseCase
import vn.geekup.domain.usecase.LoginUseCaseImpl
import vn.geekup.domain.usecase.LogoutUseCase
import vn.geekup.domain.usecase.LogoutUseCaseImpl
import vn.geekup.domain.usecase.PagingUseCase
import vn.geekup.domain.usecase.PagingUseCaseImpl

internal object DomainModules {
    val useCaseModules = module(createdAtStart = true) {
        factory<LoginUseCase> { LoginUseCaseImpl(get()) }
        factory<LogoutUseCase> { LogoutUseCaseImpl(get()) }
        factory<PagingUseCase> { PagingUseCaseImpl(get()) }
    }
}
