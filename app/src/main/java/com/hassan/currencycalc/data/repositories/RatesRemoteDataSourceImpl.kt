package com.hassan.currencycalc.data.repositories

import com.hassan.currencycalc.data.api.FixerApi
import com.hassan.currencycalc.data.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.data.mappers.ConversionResponseMapper
import com.hassan.currencycalc.data.mappers.RatesResponseMapper
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.RatesResult
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
                val response = service.getRates(base, listOf(target), startDate, endDate)
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