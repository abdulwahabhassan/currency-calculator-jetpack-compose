package com.hassan.data.repositories

import com.hassan.data.datasource.RatesRemoteDataSource
import com.hassan.domain.Result
import com.hassan.domain.entities.ConversionResult
import com.hassan.domain.entities.RatesResult
import com.hassan.domain.repositories.RatesRepository

class RatesRepositoryImpl (
    private val ratesRemoteDataSource: RatesRemoteDataSource
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

    override suspend fun convertRate(base: String, symbols: String): Result<RatesResult> {
        return ratesRemoteDataSource.convertRate(base, symbols)
    }

    override suspend fun getHistoricalRemoteRates(date: String, base: String, symbols: String): Result<RatesResult> {
        return ratesRemoteDataSource.getHistoricalRates(date, base, symbols)
    }



}