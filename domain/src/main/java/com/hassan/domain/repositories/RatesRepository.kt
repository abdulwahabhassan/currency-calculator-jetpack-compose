package com.hassan.domain.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.ConversionResult
import com.hassan.domain.entities.RatesResult

interface RatesRepository {

    suspend fun convert(
        from: String,
        to: String,
        amount: Double): Result<ConversionResult>

    suspend fun getRates(
        base: String,
        target: String,
        startDate: String,
        endDate: String): Result<RatesResult>

    suspend fun convertRate(
        base: String,
        symbols: String
    ): Result<RatesResult>

    suspend fun getHistoricalRemoteRates(
        date: String,
        base: String,
        symbols: String): Result<RatesResult>


}