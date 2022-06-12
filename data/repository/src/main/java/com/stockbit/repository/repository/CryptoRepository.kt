package com.stockbit.repository.repository

import com.stockbit.remote.datasource.RemoteDataSource
import com.stockbit.repository.AppDispatchers
import com.stockbit.repository.utils.resultFlow

class CryptoRepository(
    private val remote: RemoteDataSource,
    private val dispatcher: AppDispatchers
) {
    fun getTopTotalVolFull(limit: Int, page: Int, currency: String) = resultFlow(
        networkCall = { remote.getTopTotalVolFull(limit, page, currency) },
        dispatchers = dispatcher
    )

    fun getHistoryDataToday(fsym: String, tsym: String, limit: Int) = resultFlow(
        networkCall = { remote.getHistoryDataToday(fsym, tsym, limit)},
        dispatchers = dispatcher
    )
}