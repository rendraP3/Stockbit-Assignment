package com.stockbit.model.dto.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TopListVolumeFullDataResponse(
    @SerializedName("Data") val data: List<TopListData>,
)

data class TopListData(
    @SerializedName("CoinInfo") val coinInfo: CoinInfo,
)

@Entity
data class CoinInfo constructor(
    @PrimaryKey
    @SerializedName("Id") var id: String,
    @SerializedName("Name") var name: String,
    @SerializedName("FullName") var fullName: String,
    @SerializedName("Internal") var internal: String,
    @SerializedName("ImageUrl") var imageUrl: String,
    @SerializedName("Url") var url: String,
    var price: Double = 0.0,
    var marketCap: Double = 0.0,
    var percentage: Double = 0.0,
    var currencySymbol: String
)