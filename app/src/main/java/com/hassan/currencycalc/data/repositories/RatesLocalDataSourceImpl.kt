package com.hassan.currencycalc.data.repositories

import com.hassan.currencycalc.data.datasource.RatesLocalDataSource
import com.hassan.currencycalc.data.mappers.RatesResponseMapper
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.RatesResult

class RatesLocalDataSourceImpl(
    private val ratesDao: RatesDao,
    private val ratesResponseMapper: RatesResponseMapper,
): RatesLocalDataSource {
    override fun getRates(base: String, targetList: List<String>): Result<List<RatesResult>> {

    }


}
