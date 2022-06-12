package com.sandbox.domain.domain

import com.stockbit.repository.repository.SocketRepository

class ReceiveResponseUseCase(private val repo: SocketRepository) {
    operator fun invoke() = repo.receiveResponse()
}