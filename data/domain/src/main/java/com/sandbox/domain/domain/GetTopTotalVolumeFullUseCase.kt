package com.sandbox.domain.domain

import com.stockbit.repository.repository.CryptoRepository

class GetTopTotalVolumeFullUseCase(private val repo: CryptoRepository) {
    operator fun invoke(page: Int, currency: String) = repo.getTopTotalVolFull(
        20, page, currency
    )
}