package com.hassan.currencycalc.data.repositories

import com.hassan.currencycalc.domain.datasource.RatesLocalDataSource
import com.hassan.currencycalc.domain.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.RatesResult
import com.hassan.currencycalc.domain.repositories.RatesRepository
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val ratesRemoteDataSource: RatesRemoteDataSource,
    private val ratesLocalDataSource: RatesLocalDataSource
) : RatesRepository {

    override suspend fun getRates(
        base: String,
        target: String,
        startDate: String,
        endDate: String
    ): Result<RatesResult> {
        return ratesRemoteDataSource.getRates(base, target, startDate, endDate)
    }

    override suspend fun convert(
        from: String,
        to: String,
        amount: Double
    ): Result<ConversionResult> {
        return ratesRemoteDataSource.convert(from, to, amount)
    }


}