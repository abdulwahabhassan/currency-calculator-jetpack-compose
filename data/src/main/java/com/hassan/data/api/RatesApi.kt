package com.hassan.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

//    @GET("/latest")
//    fun getConversionRate(
//        @Query("access_key") accessKey: String,
//        @Query("base") base: String,
//        @Query("symbols") symbols: String) : Response<RatesApiResponse>

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: String = "EUR"
    ) : Response<RatesApiResponse>
}