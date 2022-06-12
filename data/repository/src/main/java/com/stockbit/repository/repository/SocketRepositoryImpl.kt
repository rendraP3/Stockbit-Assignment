package com.stockbit.repository.repository

import com.stockbit.model.dto.request.SubscriptionRequest
import com.stockbit.model.dto.response.TickerResponse
import com.stockbit.remote.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

class SocketRepositoryImpl(
    private val dataSource: RemoteDataSource
) : SocketRepository {
    override fun connectToSocket(request: SubscriptionRequest) = dataSource.connectToWebSocket(request)

    override fun receiveResponse(): Flow<TickerResponse> = dataSource.receiveResponse().consumeAsFlow()
}