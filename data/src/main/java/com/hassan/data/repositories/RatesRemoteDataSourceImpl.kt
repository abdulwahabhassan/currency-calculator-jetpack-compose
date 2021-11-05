package com.hassan.data.repositories

import com.hassan.data.api.RatesApi
import com.hassan.data.mappers.RatesApiResponseMapper
import com.hassan.domain.Result
import com.hassan.domain.entities.LatestRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class RatesRemoteDataSourceImpl(
    private val service: RatesApi,
    private val mapper: RatesApiResponseMapper
) : RatesRemoteDataSource {
    override suspend fun getLatestRates(): Result<LatestRates> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getLatestRates()
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toLatestRates(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun convertRate(symbol: String): Result<LatestRates> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.convertRate(symbols = symbol)
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toLatestRates(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}