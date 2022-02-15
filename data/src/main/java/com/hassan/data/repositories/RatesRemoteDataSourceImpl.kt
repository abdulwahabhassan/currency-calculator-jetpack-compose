package com.hassan.data.repositories

import com.hassan.data.api.RatesApi
import com.hassan.data.datasource.RatesRemoteDataSource
import com.hassan.data.mappers.RatesResponseMapper
import com.hassan.domain.Result
import com.hassan.domain.entities.RatesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class RatesRemoteDataSourceImpl(
    private val service: RatesApi,
    private val mapper: RatesResponseMapper
) : RatesRemoteDataSource {
    override suspend fun getLatestRates(base: String): Result<RatesResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getLatestRates(base)
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toRates(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun convertRate(base: String, symbols: String): Result<RatesResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.convertRate(base = base, symbols = symbols)
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toRates(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun getHistoricalRates(date: String, base: String, symbols: String): Result<RatesResult> =
        withContext(Dispatchers.IO) {
        try {
            val response = service.getHistoricalRates(date = date, base = base, symbols = symbols)
            if (response.isSuccessful) {
                return@withContext Result.Success(mapper.toRates(response.body()!!))
            } else {
                return@withContext Result.Error(Exception(response.message()))
            }
        }catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }


}