package com.sandbox.domain.domain

import com.stockbit.model.dto.request.SubscriptionRequest
import com.stockbit.repository.repository.SocketRepository

class ConnectToWebSocketUseCase(private val repo: SocketRepository) {
    operator fun invoke(request: SubscriptionRequest) = repo.connectToSocket(request)
}