package com.hassan.currencycalc.data.models

import com.squareup.moshi.Json

data class ErrorRemote (
    @Json(name = "code")
    val code: Int?,
    @Json(name = "info")
    val info: String?
)

