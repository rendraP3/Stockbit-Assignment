package com.sandbox.domain.domain

import com.stockbit.repository.repository.CryptoRepository

class GetHistoryDataToday(private val repo: CryptoRepository) {
    operator fun invoke(fsym: String, tsym: String) = repo.getHistoryDataToday(
        fsym, tsym, limit = 50
    )
}