package com.stockbit.model.dto.request

import com.google.gson.annotations.SerializedName

data class SubscriptionRequest (
    @SerializedName("action")
    val action: String,
    @SerializedName("subs")
    val subs: List<String>
)