package com.hassan.currencycalc.data.models

import com.google.gson.annotations.SerializedName

data class ErrorRemote (
    @SerializedName("code")
    val code: Int?,
    @SerializedName("info")
    val info: String?
)

