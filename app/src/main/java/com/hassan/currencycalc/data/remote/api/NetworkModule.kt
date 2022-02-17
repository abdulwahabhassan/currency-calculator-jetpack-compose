package com.hassan.currencycalc.data.remote.api

import com.hassan.currencycalc.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class NetworkModule @Inject constructor (
    private val moshiConverterFactory: MoshiConverterFactory
    ) {

    //lazily initialize request interceptor
    private val requestInterceptor by lazy {
        Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("access_key", BuildConfig.FIXER_API_ACCESS_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    //lazily initialize http client
    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
    }

    //build retrofit with
    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()

    }


    //creates an implementation of rates api end points
    fun createRatesApi(baseUrl: String): FixerApi {
        return getRetrofit(baseUrl).create(FixerApi::class.java)
    }

}