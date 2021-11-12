package com.hassan.data.api

import com.google.gson.annotations.SerializedName

data class RatesApiResponse (
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("timestamp")
    var timestamp: Long?,
    @SerializedName("historical")
    var historical: Boolean?,
    @SerializedName("base")
    var base: String?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("rates")
    var rates: Map<String, Double>?
        )

