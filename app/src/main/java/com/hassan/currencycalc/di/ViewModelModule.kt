package com.hassan.currencycalc.di

import android.content.Context
import androidx.room.Room
import com.hassan.currencycalc.data.local.database.AppDatabase
import com.hassan.currencycalc.data.local.database.dao.RatesLocalDao
import com.hassan.currencycalc.data.local.datasource.RatesLocalDataSourceImpl
import com.hassan.currencycalc.data.local.mappers.RatesLocalMapper
import com.hassan.currencycalc.data.remote.api.NetworkModule
import com.hassan.currencycalc.data.remote.api.FixerApi
import com.hassan.currencycalc.domain.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.data.remote.mappers.ConversionRemoteMapper
import com.hassan.currencycalc.data.remote.datasource.RatesRemoteDataSourceImpl
import com.hassan.currencycalc.data.remote.mappers.RatesRemoteMapper
import com.hassan.currencycalc.data.repositories.RatesRepositoryImpl
import com.hassan.currencycalc.domain.datasource.RatesLocalDataSource
import com.hassan.currencycalc.domain.repositories.RatesRepository
import com.hassan.currencycalc.domain.usecases.ConvertUseCase
import com.hassan.currencycalc.domain.usecases.GetRatesUseCase
import com.hassan.currencycalc.utilities.Helpers
import com.hassan.currencycalc.utilities.Helpers.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideGetRatesUseCase(
        ratesRepository: RatesRepository
    ): GetRatesUseCase {
        return GetRatesUseCase(ratesRepository)
    }

    @ViewModelScoped
    @Provides
    fun providesConvertUseCase(
        ratesRepository: RatesRepository
    ): ConvertUseCase {
        return ConvertUseCase(ratesRepository)
    }
}