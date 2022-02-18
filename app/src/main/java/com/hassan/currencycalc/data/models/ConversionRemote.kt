package com.hassan.currencycalc.data.models

import com.google.gson.annotations.SerializedName

data class ConversionRemote(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("result")
    val result: Double?,
    @SerializedName("error")
    var errorRemote: ErrorRemote?
)
