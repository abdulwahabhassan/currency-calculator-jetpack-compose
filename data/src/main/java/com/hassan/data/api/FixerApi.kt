package com.hassan.data.api

import com.hassan.data.models.ConversionResponse
import com.hassan.data.models.RatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerApi {

    @GET("latest")
    suspend fun convertRate(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ) : Response<RatesResponse>

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: String
    ) : Response<RatesResponse>

    @GET("{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<RatesResponse>

    //convert from base currency to target currency
    @GET("convert")
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<ConversionResponse>

    //get historical rates between any two dates
    @GET("timeseries")
    suspend fun getRates(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") base: String,
        @Query("symbols") target: String
    ): Response<RatesResponse>
}