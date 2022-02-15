package com.hassan.data.datasource

import com.hassan.domain.Result
import com.hassan.domain.entities.RatesResult

interface RatesRemoteDataSource {

    suspend fun getLatestRates(base: String) : Result<RatesResult>

    suspend fun convertRate(base: String, symbols: String): Result<RatesResult>

    suspend fun getHistoricalRates(date: String, base: String, symbols: String): Result<RatesResult>
}