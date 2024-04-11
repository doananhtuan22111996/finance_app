package vn.geekup.data

import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory
import vn.geekup.data.local.AppDatabase
import vn.geekup.data.local.PreferenceWrapper
import vn.geekup.data.network.TokenAuthenticator
import vn.geekup.data.repository.AuthRepositoryImpl
import vn.geekup.data.repository.PagingRepositoryImpl
import vn.geekup.data.service.ApiService
import vn.geekup.domain.repository.AuthRepository
import vn.geekup.domain.repository.PagingRepository

object DataModule {
    val localModules = module(createdAtStart = true) {
        single { GsonConverterFactory.create() }
        single { PreferenceWrapper(androidContext()) }
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().itemDao() }
    }

    val remoteModules = module(createdAtStart = true) {
        single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
        single { TokenAuthenticator(get()) }
        single {
            Providers.provideOkHttpClient(
                get(), get(), get(), Providers.provideCache(androidContext())
            )
        }
        single { Providers.provideRetrofit<ApiService>(get(), get()) }
    }

    var repositoryModules = module(createdAtStart = true) {
        factory<AuthRepository> {
            AuthRepositoryImpl(get(), get())
        }

        factory<PagingRepository> {
            PagingRepositoryImpl(get(), get())
        }
    }
}
