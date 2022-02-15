package com.hassan.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

    //lazily initialize request interceptor
    private val requestInterceptor by lazy {
        Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("access_key", API_ACCESS_KEY)
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    //creates an implementation of rates api end points
    fun createRatesApi(baseUrl: String): FixerApi {
        return getRetrofit(baseUrl).create(FixerApi::class.java)
    }

    companion object {
        private const val API_ACCESS_KEY = "89abab446cef1a3b933d1eb6270057b7"
    }
}