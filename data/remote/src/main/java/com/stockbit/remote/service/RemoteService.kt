package com.stockbit.remote.service

import com.stockbit.model.dto.response.HistoricalResponse
import com.stockbit.model.dto.response.TopListVolumeFullDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("top/totalvolfull")
    suspend fun getTopTotalVolFull(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("tsym") tsym: String
    ): Response<TopListVolumeFullDataResponse>

    @GET("v2/histoday")
    suspend fun getHistoryDataToday(
        @Query("fsym") fsym: String,
        @Query("tsym") tsym: String,
        @Query("limit") limit: Int
    ): Response<HistoricalResponse>

}