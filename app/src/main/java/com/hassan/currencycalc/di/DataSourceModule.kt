package com.hassan.currencycalc.di

import android.content.Context
import androidx.room.Room
import com.hassan.currencycalc.data.local.database.AppDatabase
import com.hassan.currencycalc.data.local.database.dao.RatesLocalDao
import com.hassan.currencycalc.data.local.datasource.RatesLocalDataSourceImpl
import com.hassan.currencycalc.data.local.mappers.RatesLocalMapper
import com.hassan.currencycalc.data.remote.api.FixerApi
import com.hassan.currencycalc.data.remote.datasource.RatesRemoteDataSourceImpl
import com.hassan.currencycalc.data.remote.mappers.ConversionRemoteMapper
import com.hassan.currencycalc.data.remote.mappers.RatesRemoteMapper
import com.hassan.currencycalc.domain.datasource.RatesLocalDataSource
import com.hassan.currencycalc.domain.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.utilities.Helpers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RatesDataSourceModule {

    @Provides
    fun provideRatesRemoteDataSource(
        fixerApi: FixerApi,
        ratesResponseMapper: RatesRemoteMapper,
        conversionRemoteMapper: ConversionRemoteMapper
    ): RatesRemoteDataSource {
        return RatesRemoteDataSourceImpl(
            fixerApi,
            ratesResponseMapper,
            conversionRemoteMapper)
    }

    @Provides
    fun providesRatesLocalDataSource(
        ratesLocalDao: RatesLocalDao,
        ratesLocalMapper: RatesLocalMapper,
    ): RatesLocalDataSource {
        return RatesLocalDataSourceImpl(ratesLocalDao, ratesLocalMapper)
    }
}