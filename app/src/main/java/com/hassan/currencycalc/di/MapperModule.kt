package com.hassan.currencycalc.di

import com.hassan.currencycalc.data.local.mappers.RatesLocalMapper
import com.hassan.currencycalc.data.remote.mappers.ConversionRemoteMapper
import com.hassan.currencycalc.data.remote.mappers.RatesRemoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideRatesRemoteMapper(): RatesRemoteMapper {
        return RatesRemoteMapper()
    }

    @Provides
    fun provideConversionRemoteMapper(): ConversionRemoteMapper {
        return ConversionRemoteMapper()
    }

    @Provides
    fun providesRatesLocalMapper(): RatesLocalMapper {
        return RatesLocalMapper()
    }
}