package com.hassan.currencycalc.data.models

import com.squareup.moshi.Json

data class ConversionRemote(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "result")
    val result: Double?,
    @Json(name = "error")
    val errorRemote: ErrorRemote?
)
