package com.hassan.data.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.LatestRates
import com.hassan.domain.repositories.RatesRepository

class RatesRepositoryImpl (
    private val ratesRemoteDataSource: RatesRemoteDataSource
) : RatesRepository {
    override suspend fun getRemoteRates(): Result<LatestRates> {
        return ratesRemoteDataSource.getLatestRates()
    }

    override suspend fun convertRate(symbol: String): Result<LatestRates> {
        return ratesRemoteDataSource.convertRate(symbol)
    }

}