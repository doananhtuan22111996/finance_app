package vn.finance.data

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.finance.data.local.PreferenceWrapper
import vn.finance.data.network.NullOrEmptyConverterFactory
import vn.finance.data.network.TokenAuthenticator
import java.io.File
import java.util.concurrent.TimeUnit

internal object Providers {
    fun provideCache(context: Context): Cache {
        val file = File(context.cacheDir, Config.Cache.CACHE_FILE_NAME)
        val isSuccess = file.mkdirs()
        return if (isSuccess) {
            Cache(file, Config.Cache.CACHE_FILE_SIZE)
        } else Cache(context.cacheDir, Config.Cache.CACHE_FILE_SIZE)
    }

    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        preferenceWrapper: PreferenceWrapper,
        cache: Cache,
    ): OkHttpClient {
        val builder =
            OkHttpClient.Builder().readTimeout(Config.TimeOut.TIMEOUT_READ_SECOND, TimeUnit.SECONDS)
                .connectTimeout(Config.TimeOut.TIMEOUT_CONNECT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(Config.TimeOut.TIMEOUT_WRITE_SECOND, TimeUnit.SECONDS)
                .addNetworkInterceptor(
                    Interceptor { chain ->
                        var request = chain.request()
                        val builder = request.newBuilder()
                        val token =
                            preferenceWrapper.getString(Config.SharePreference.KEY_AUTH_TOKEN, "")
                        if (token.isNotEmpty()) {
                            builder.addHeader("Authorization", "Bearer $token")
                        }
                        request = builder.build()
                        chain.proceed(request)
                    },
                ).authenticator(tokenAuthenticator)
        if (Config.isDebug) {
            builder.addInterceptor(httpLoggingInterceptor)
        } else {
            builder.cache(cache)
        }
        return builder.build()
    }

    inline fun <reified T> provideRetrofit(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): T {
        val retrofit = Retrofit.Builder().baseUrl(Config.mainDomain).client(okHttpClient)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory).build()
        return retrofit.create(T::class.java)
    }
}
