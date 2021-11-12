package com.hassan.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    suspend fun convertRate(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ) : Response<RatesApiResponse>

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: String
    ) : Response<RatesApiResponse>

    @GET("{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<RatesApiResponse>
}