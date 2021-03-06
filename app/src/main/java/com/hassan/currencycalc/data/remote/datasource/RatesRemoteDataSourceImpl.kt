package com.hassan.currencycalc.data.remote.datasource

import com.hassan.currencycalc.data.remote.api.FixerApi
import com.hassan.currencycalc.domain.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.data.remote.mappers.ConversionRemoteMapper
import com.hassan.currencycalc.data.remote.mappers.RatesRemoteMapper
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.RatesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception

class RatesRemoteDataSourceImpl @Inject constructor(
    private val service: FixerApi,
    private val ratesRemoteMapper: RatesRemoteMapper,
    private val conversionRemoteMapper: ConversionRemoteMapper
) : RatesRemoteDataSource {

    override suspend fun convert(from: String, to: String, amount: Double):
            Result<ConversionResult> = withContext(Dispatchers.IO) {
        try {
            //perform request
            val response = service.convert(from = from, to = to, amount = amount)
            //if network response was successful,
                //map the body of the response to the corresponding domain entity and wrap it in a
                    //successful result
            if (response.isSuccessful) {
                Timber.d("success ${response.body()!!}")
                return@withContext Result.Success(
                    conversionRemoteMapper.mapToEntity(response.body()!!)
                )
            } else {
                Timber.d("unsuccessful ${response.body()!!}")
                //if network response was unsuccessful, retrieve the message of the response,
                    //initialize an exception wrapped in an error result
                return@withContext Result.Error(
                    Exception(response.message())
                )
            }
        } catch (e: Exception) { //catch any exception that may be thrown when performing this
            //request (not generated by the http request itself), wrap it in an error result
            return@withContext Result.Error(e)
        }
    }

    override suspend fun getRates(base: String, target: String, startDate: String, endDate: String):
            Result<RatesResult> =
        withContext(Dispatchers.IO) {
            try {
                //perform request
                val response = service.getRates(base, listOf(target), startDate, endDate)
                //if network response was successful,
                //map the body of the response to the corresponding domain entity and wrap it in a
                //successful result
                if (response.isSuccessful) {
                    Timber.d("success ${response.body()!!}")
                    return@withContext Result.Success(
                        ratesRemoteMapper.mapToEntity(response.body()!!)
                    )
                } else {
                    Timber.d("unsuccessful ${response.body()!!}")
                    //if network response was unsuccessful, retrieve the message of the response,
                    //initialize an exception wrapped in an error result
                    return@withContext Result.Error(
                        Exception(response.message())
                    )
                }
            } catch (e: Exception) { //catch any exception that may be thrown when performing this
                //request(not generated by the http request itself), wrap it in an error result
                return@withContext Result.Error(e)
            }
        }

}