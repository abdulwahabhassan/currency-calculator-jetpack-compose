package com.hassan.data.models

import com.google.gson.annotations.SerializedName

data class RatesResponse (
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("timeseries")
    var timeSeries: Boolean?,
    @SerializedName("startDate")
    var startDate: String?,
    @SerializedName("endDate")
    var endDate: String?,
    @SerializedName("base")
    var base: String?,
    @SerializedName("rates")
    var rates: Map<String, Map<String, Double>>?
        )

