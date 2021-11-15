package com.hassan.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

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

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    fun createRatesApi(baseUrl: String): RatesApi {
        return getRetrofit(baseUrl).create(RatesApi::class.java)
    }

    companion object {
        private const val API_ACCESS_KEY = "4fd19d71017e78d17d742646003ff985"
    }
}