package com.hassan.currencycalc.domain.entities


data class RatesResult(
    val success: Boolean? = false,
    val timeSeries: Boolean? = false,
    val startDate: String? = null,
    val endDate: String? = null,
    val base: String? = null,
    val rates: Map<String, Map<String, Double>>? = null,
    val error: Error? = null
)
