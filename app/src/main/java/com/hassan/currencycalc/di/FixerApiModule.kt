package com.hassan.currencycalc.di

import com.hassan.currencycalc.data.remote.api.FixerApi
import com.hassan.currencycalc.data.remote.api.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FixerApiModule {

    private const val LATEST_RATE_API_BASE_URL = "http://data.fixer.io/api/"

    @Provides
    @Singleton
    fun providesRatesApi (
        moshiConverterFactory: MoshiConverterFactory
    ) : FixerApi {
        return NetworkModule(moshiConverterFactory).createRatesApi(LATEST_RATE_API_BASE_URL)
    }
}