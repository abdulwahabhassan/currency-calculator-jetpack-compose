package com.hassan.data.repositories

import com.hassan.data.api.FixerApi
import com.hassan.data.datasource.RatesRemoteDataSource
import com.hassan.data.mappers.ConversionResponseMapper
import com.hassan.data.mappers.RatesResponseMapper
import com.hassan.domain.Result
import com.hassan.domain.entities.ConversionResult
import com.hassan.domain.entities.RatesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class RatesRemoteDataSourceImpl(
    private val service: FixerApi,
    private val ratesResponseMapper: RatesResponseMapper,
    private val conversionResponseMapper: ConversionResponseMapper
) : RatesRemoteDataSource {

    override suspend fun convert(from: String, to: String, amount: Double):
            Result<ConversionResult> = withContext(Dispatchers.IO) {
        try {
            val response = service.convert(from = from, to = to, amount = amount)
            if (response.isSuccessful) {
                return@withContext Result.Success(conversionResponseMapper.toConversionResult(response.body()!!))
            } else {
                return@withContext Result.Error(Exception(response.message()))
            }
        }catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun getRates(base: String, target: String, startDate: String, endDate: String):
            Result<RatesResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getRates(base, target, startDate, endDate)
                if (response.isSuccessful) {
                    return@withContext Result.Success(ratesResponseMapper.toRatesResult(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }


    override suspend fun getLatestRates(base: String): Result<RatesResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getLatestRates(base)
                if (response.isSuccessful) {
                    return@withContext Result.Success(ratesResponseMapper.toRatesResult(response.body()!!))
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
                    return@withContext Result.Success(ratesResponseMapper.toRatesResult(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun getHistoricalRates(date: String, base: String, symbols: String):
            Result<RatesResult> = withContext(Dispatchers.IO) {
        try {
            val response = service.getHistoricalRates(date = date, base = base, symbols = symbols)
            if (response.isSuccessful) {
                return@withContext Result.Success(ratesResponseMapper.toRatesResult(response.body()!!))
            } else {
                return@withContext Result.Error(Exception(response.message()))
            }
        }catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }




}