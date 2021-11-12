package com.hassan.data.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.Rates
import com.hassan.domain.repositories.RatesRepository

class RatesRepositoryImpl (
    private val ratesRemoteDataSource: RatesRemoteDataSource
) : RatesRepository {
    override suspend fun getRemoteRates(base: String): Result<Rates> {
        return ratesRemoteDataSource.getLatestRates(base)
    }

    override suspend fun convertRate(base: String, symbols: String): Result<Rates> {
        return ratesRemoteDataSource.convertRate(base, symbols)
    }

    override suspend fun getHistoricalRemoteRates(date: String, base: String, symbols: String): Result<Rates> {
        return ratesRemoteDataSource.getHistoricalRates(date, base, symbols)
    }

}