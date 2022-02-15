package com.hassan.data.models

import com.google.gson.annotations.SerializedName

data class ConversionResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: Double?
)
