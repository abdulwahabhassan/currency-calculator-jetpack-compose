package com.hassan.data.repositories

import com.hassan.domain.Result
import com.hassan.domain.entities.LatestRates
import com.hassan.domain.repositories.RatesRepository

class RatesRepositoryImpl (
    private val remoteDataSource: RatesRemoteDataSource
) : RatesRepository {
    override suspend fun getRemoteRates(): Result<LatestRates> {
        return remoteDataSource.getLatestRates()
    }

}