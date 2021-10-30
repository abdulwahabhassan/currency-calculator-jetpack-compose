package com.hassan.domain.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.LatestRates

interface RatesRepository {
    suspend fun getRemoteRates(): Result<LatestRates>
}