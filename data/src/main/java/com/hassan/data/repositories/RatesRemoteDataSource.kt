package com.hassan.data.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.Rates

interface RatesRemoteDataSource {

    suspend fun getLatestRates(base: String) : Result<Rates>

    suspend fun convertRate(base: String, symbols: String): Result<Rates>

    suspend fun getHistoricalRates(date: String, base: String, symbols: String): Result<Rates>
}