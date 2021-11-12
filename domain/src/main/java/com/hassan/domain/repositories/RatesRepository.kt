package com.hassan.domain.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.Rates

interface RatesRepository {
    suspend fun getRemoteRates(base: String): Result<Rates>
    suspend fun convertRate(base: String, symbols: String): Result<Rates>
    suspend fun getHistoricalRemoteRates(date: String, base: String, symbols: String): Result<Rates>
}