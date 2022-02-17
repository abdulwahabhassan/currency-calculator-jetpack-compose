package com.hassan.currencycalc.domain.datasource

import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.RatesResult

interface RatesLocalDataSource {
    suspend fun getRates(
        base: String,
        targetList: List<String>
    ): Result<List<RatesResult>>
}