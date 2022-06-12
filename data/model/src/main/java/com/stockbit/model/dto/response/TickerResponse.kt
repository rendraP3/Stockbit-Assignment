package com.stockbit.model.dto.response

import com.google.gson.annotations.SerializedName

data class TickerResponse (
    @SerializedName("TYPE")
    val type: Int,
    @SerializedName("FROMSYMBOL")
    val fromSymbol: String,
    @SerializedName("TOSYMBOL")
    val toSymbol: String,
    @SerializedName("PRICE")
    val price: Double,
    @SerializedName("OPENDAY")
    val openDay: Double,
    @SerializedName("VOLUME24HOURTO")
    val volume: Double
)