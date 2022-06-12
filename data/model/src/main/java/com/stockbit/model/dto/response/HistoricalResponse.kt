package com.stockbit.model.dto.response

import com.google.gson.annotations.SerializedName

data class HistoricalResponse(
    @SerializedName("Data") val data: HistoricalData
)

data class HistoricalData(
    @SerializedName("Aggregated") val aggregated: Boolean,
    @SerializedName("TimeFrom") val timeFrom: Long,
    @SerializedName("TimeTo") val timeTo: Long,
    @SerializedName("Data") val data: List<HistoricalDetail>
)

data class HistoricalDetail(
    @SerializedName("time") val time: Long,
    @SerializedName("high") val high: Double,
    @SerializedName("low") val low: Double,
    @SerializedName("open") val open: Double
)