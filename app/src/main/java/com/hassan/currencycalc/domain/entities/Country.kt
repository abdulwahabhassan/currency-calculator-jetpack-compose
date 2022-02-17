package com.hassan.currencycalc.domain.entities

data class Country (
    val isoAlpha3: String,
    val currency: Currency,
    val flag: String
)