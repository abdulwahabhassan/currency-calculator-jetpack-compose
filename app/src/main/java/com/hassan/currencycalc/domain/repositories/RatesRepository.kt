package com.hassan.currencycalc.domain.repositories

import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.RatesResult

interface RatesRepository {

    suspend fun convert(
        from: String,
        to: String,
        amount: Double
    ): Result<ConversionResult>

    suspend fun getRates(
        base: String,
        target: String,
        startDate: String,
        endDate: String
    ): Result<RatesResult>

}