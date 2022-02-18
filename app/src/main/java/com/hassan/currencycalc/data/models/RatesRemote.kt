package com.hassan.currencycalc.data.models


import com.squareup.moshi.Json

data class RatesRemote (
    @Json(name = "success")
    var success: Boolean?,
    @Json(name = "timeseries")
    var timeSeries: Boolean?,
    @Json(name = "startDate")
    var startDate: String?,
    @Json(name = "endDate")
    var endDate: String?,
    @Json(name = "base")
    var base: String?,
    @Json(name = "rates")
    var rates: Map<String, Map<String, Double>>?,
    @Json(name = "error")
    var errorRemote: ErrorRemote?
        )