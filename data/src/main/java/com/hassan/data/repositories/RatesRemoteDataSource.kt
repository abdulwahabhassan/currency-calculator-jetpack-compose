package com.hassan.data.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.LatestRates

interface RatesRemoteDataSource {
    suspend fun getLatestRates() : Result<LatestRates>
}