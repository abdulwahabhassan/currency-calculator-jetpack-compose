package com.hassan.currencycalc.di

import android.net.ConnectivityManager
import com.hassan.currencycalc.data.repositories.RatesRepositoryImpl
import com.hassan.currencycalc.domain.datasource.RatesLocalDataSource
import com.hassan.currencycalc.domain.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.domain.repositories.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesRatesRepository(
        ratesRemoteDataSource: RatesRemoteDataSource,
        ratesLocalDataSource: RatesLocalDataSource,
        connectivityManager: ConnectivityManager
    ): RatesRepository {
        return RatesRepositoryImpl(
            ratesRemoteDataSource, ratesLocalDataSource, connectivityManager
        )
    }

}