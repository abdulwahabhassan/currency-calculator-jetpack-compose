package com.hassan.currencycalc.data.datasource

import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.RatesResult

interface RatesLocalDataSource {
    fun getRates(
        base: String,
        targetList: List<String>
    ): Result<List<RatesResult>>
}