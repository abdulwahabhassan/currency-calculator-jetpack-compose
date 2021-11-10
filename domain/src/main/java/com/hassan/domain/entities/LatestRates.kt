package com.hassan.domain.entities

data class LatestRates(
    val success: Boolean? = null,
    val timestamp: Long? = null,
    val base: String? = null,
    val date: String? = null,
    val rates: Map<String, Double>? = null
)
