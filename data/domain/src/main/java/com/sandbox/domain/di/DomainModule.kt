package com.sandbox.domain.di

import com.sandbox.domain.domain.ConnectToWebSocketUseCase
import com.sandbox.domain.domain.GetHistoryDataToday
import com.sandbox.domain.domain.GetTopTotalVolumeFullUseCase
import com.sandbox.domain.domain.ReceiveResponseUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetTopTotalVolumeFullUseCase(get()) }
    factory { ConnectToWebSocketUseCase(get()) }
    factory { ReceiveResponseUseCase(get()) }
    factory { GetHistoryDataToday(get()) }
}