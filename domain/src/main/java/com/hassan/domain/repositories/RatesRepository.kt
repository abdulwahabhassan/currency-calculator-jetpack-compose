package com.hassan.domain.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.RatesResult

interface RatesRepository {
    suspend fun getRemoteRates(base: String): Result<RatesResult>
    suspend fun convertRate(base: String, symbols: String): Result<RatesResult>
    suspend fun getHistoricalRemoteRates(date: String, base: String, symbols: String): Result<RatesResult>
}