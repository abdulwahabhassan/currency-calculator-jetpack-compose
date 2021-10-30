package com.hassan.currencycalc.di

import android.content.Context
import com.hassan.data.api.NetworkModule
import com.hassan.data.mappers.RatesApiResponseMapper
import com.hassan.data.repositories.RatesRemoteDataSourceImpl
import com.hassan.data.repositories.RatesRepositoryImpl

object ServiceLocator {

    private const val LATEST_RATE_API_BASE_URL = "http://data.fixer.io/api/"
    private val networkModule by lazy {
        NetworkModule()
    }

    @Volatile
    var ratesRepository: RatesRepositoryImpl? = null

    fun provideRatesRepository(context: Context): RatesRepositoryImpl {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return ratesRepository ?: createRatesRepository(context)
        }
    }

    private fun createRatesRepository(context: Context): RatesRepositoryImpl {
        val newRepo =
            RatesRepositoryImpl(
                RatesRemoteDataSourceImpl(
                    networkModule.createRatesApi(LATEST_RATE_API_BASE_URL),
                    RatesApiResponseMapper()
                )
            )
        ratesRepository = newRepo
        return newRepo
    }
}