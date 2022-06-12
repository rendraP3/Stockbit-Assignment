package com.stockbit.remote.service

import com.stockbit.model.dto.request.SubscriptionRequest
import com.stockbit.model.dto.response.TickerResponse
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

interface SocketService {
    @Send
    fun connectToWebSocket(request: SubscriptionRequest)

    @Receive
    fun receiveResponse() : ReceiveChannel<TickerResponse>
}