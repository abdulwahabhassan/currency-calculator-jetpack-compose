package com.hassan.data.datasource

import com.hassan.domain.Result
import com.hassan.domain.entities.ConversionResult
import com.hassan.domain.entities.RatesResult

interface RatesRemoteDataSource {

    suspend fun convert(
        from: String,
        to: String,
        amount: Double
    ): Result<ConversionResult>

    suspend fun getRates(
        base: String,
        target: String,
        startDate: String,
        endDate: String
    ): Result<RatesResult>

}