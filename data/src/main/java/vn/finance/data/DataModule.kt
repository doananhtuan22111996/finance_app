package vn.finance.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory
import vn.finance.data.local.AppDatabase
import vn.finance.data.local.PreferenceWrapper
import vn.finance.data.local.dao.ItemDao
import vn.finance.data.network.TokenAuthenticator
import vn.finance.data.repository.AuthRepositoryImpl
import vn.finance.data.repository.OnBoardingRepositoryImpl
import vn.finance.data.repository.PagingRepositoryImpl
import vn.finance.data.service.ApiService
import vn.finance.domain.repository.AuthRepository
import vn.finance.domain.repository.OnBoardingRepository
import vn.finance.domain.repository.PagingRepository

object DataModule {
    val localModules = module(createdAtStart = true) {
        single { GsonConverterFactory.create() }
        single { PreferenceWrapper(androidContext()) }
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().itemDao() }
    }

    val remoteModules = module(createdAtStart = true) {
        single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
        single { TokenAuthenticator(get<PreferenceWrapper>()) }
        single {
            Providers.provideOkHttpClient(
                get<HttpLoggingInterceptor>(),
                get<TokenAuthenticator>(),
                get<PreferenceWrapper>(),
                Providers.provideCache(androidContext())
            )
        }
        single {
            Providers.provideRetrofit<ApiService>(
                get<OkHttpClient>(), get<GsonConverterFactory>()
            )
        }
    }

    val repositoryModules = module {
        factory<OnBoardingRepository> {
            OnBoardingRepositoryImpl(get<PreferenceWrapper>())
        }

        factory<AuthRepository> {
            AuthRepositoryImpl(get<ApiService>(), get<PreferenceWrapper>())
        }

        factory<PagingRepository> {
            PagingRepositoryImpl(get<ApiService>(), get<ItemDao>())
        }
    }
}
