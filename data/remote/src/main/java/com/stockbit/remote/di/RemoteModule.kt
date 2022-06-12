package com.stockbit.remote.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.stockbit.remote.BuildConfig
import com.stockbit.remote.datasource.RemoteDataSource
import com.stockbit.remote.service.RemoteService
import com.stockbit.remote.service.SocketService
import com.stockbit.remote.utils.Constants.API_KEY
import com.stockbit.remote.utils.Constants.AUTHORIZATION
import com.stockbit.remote.utils.Constants.BACKOFF_DURATION_BASE
import com.stockbit.remote.utils.Constants.BACKOFF_DURATION_MAX
import com.stockbit.remote.utils.Constants.TIMEOUT
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.ExponentialBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {

    factory<Interceptor> {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor {
                val newRequest = it.request().newBuilder()
                    .addHeader(AUTHORIZATION, API_KEY)
                    .build()

                return@addInterceptor it.proceed(newRequest)
            }
            .addInterceptor(ChuckerInterceptor(androidContext()))
//            .addInterceptor(get<HttpLoggingInterceptor>())
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    factory {
        Gson().newBuilder()
            .serializeNulls()
            .create()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    single {
        Scarlet.Builder()
            .webSocketFactory(get<OkHttpClient>().newWebSocketFactory(BuildConfig.WEBSOCKET_BASE_URL))
            .addMessageAdapterFactory(GsonMessageAdapter.Factory(get()))
            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
            .backoffStrategy(
                ExponentialBackoffStrategy(
                    BACKOFF_DURATION_BASE,
                    BACKOFF_DURATION_MAX
                )
            )
            .build()
    }

    factory { get<Scarlet>().create(SocketService::class.java) }

    factory { get<Retrofit>().create(RemoteService::class.java) }

    factory { RemoteDataSource(get(), get()) }
}