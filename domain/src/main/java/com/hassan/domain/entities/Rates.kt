package com.hassan.domain.entities

data class Rates(
    val success: Boolean? = null,
    val timestamp: Long? = null,
    val historical: Boolean? = null,
    val base: String? = null,
    val date: String? = null,
    val rates: Map<String, Double>? = null
)
