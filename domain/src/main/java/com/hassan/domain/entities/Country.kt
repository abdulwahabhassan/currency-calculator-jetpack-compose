package com.hassan.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore

data class Country (
    val isoAlpha3: String,
    val currency: Currency,
    val flag: String
)

data class Currency(
    val code: String
)