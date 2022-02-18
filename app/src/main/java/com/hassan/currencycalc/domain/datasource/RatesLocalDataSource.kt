package com.hassan.currencycalc.domain.datasource

import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.RatesResult

interface RatesLocalDataSource {
    suspend fun getRates(
        base: String,
        target: String
    ): Result<RatesResult>
}