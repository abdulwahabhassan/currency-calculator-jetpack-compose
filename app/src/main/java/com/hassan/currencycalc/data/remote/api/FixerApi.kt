package com.hassan.currencycalc.data.remote.api

import com.hassan.currencycalc.data.models.ConversionRemote
import com.hassan.currencycalc.data.models.RatesRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FixerApi {

    //convert from base currency to target currency
    @GET("convert")
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<ConversionRemote>

    //get historical rates between any two dates
    @GET("timeseries")
    suspend fun getRates(
        @Query("base") base: String,
        @Query("symbols") target: List<String>,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String

    ): Response<RatesRemote>
}