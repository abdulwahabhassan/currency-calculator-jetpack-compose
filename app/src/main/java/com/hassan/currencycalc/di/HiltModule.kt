package com.hassan.currencycalc.di

import com.hassan.currencycalc.data.api.NetworkModule
import com.hassan.currencycalc.data.api.FixerApi
import com.hassan.currencycalc.data.mappers.RatesResponseMapper
import com.hassan.currencycalc.data.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.data.mappers.ConversionResponseMapper
import com.hassan.currencycalc.data.repositories.RatesRemoteDataSourceImpl
import com.hassan.currencycalc.data.repositories.RatesRepositoryImpl
import com.hassan.currencycalc.domain.repositories.RatesRepository
import com.hassan.currencycalc.domain.usecases.ConvertUseCase
import com.hassan.currencycalc.domain.usecases.GetRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RatesRepositoryModule {

    @Singleton
    @Provides
    fun providesRatesRepository(
        ratesRemoteDataSource: RatesRemoteDataSource
    ): RatesRepository {
        return RatesRepositoryImpl(
            ratesRemoteDataSource
        )
    }

}

@Module
@InstallIn(SingletonComponent::class)
object RatesApiModule {

    private const val LATEST_RATE_API_BASE_URL = "http://data.fixer.io/api/"

    @Provides
    fun providesRatesApi () : FixerApi {
        return NetworkModule().createRatesApi(LATEST_RATE_API_BASE_URL)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideRatesResponseMapper(): RatesResponseMapper {
        return RatesResponseMapper()
    }

    @Provides
    fun provideConversionResponseMapper() : ConversionResponseMapper {
        return ConversionResponseMapper()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RatesRemoteDataSourceModule {

    @Provides
    fun provideRatesRemoteDataSource(
        fixerApi: FixerApi,
        ratesResponseMapper: RatesResponseMapper,
        conversionResponseMapper: ConversionResponseMapper
    ) : RatesRemoteDataSource {
        return RatesRemoteDataSourceImpl(
            fixerApi,
            ratesResponseMapper,
            conversionResponseMapper)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideGetRatesUseCase(
        ratesRepository: RatesRepository
    ) : GetRatesUseCase {
        return GetRatesUseCase(ratesRepository)
    }

    @ViewModelScoped
    @Provides
    fun providesConvertUseCase(
        ratesRepository: RatesRepository
    ) : ConvertUseCase {
        return ConvertUseCase(ratesRepository)
    }
}