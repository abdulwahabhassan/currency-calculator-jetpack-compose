package com.hassan.currencycalc.di

import com.hassan.data.api.NetworkModule
import com.hassan.data.api.RatesApi
import com.hassan.data.mappers.RatesApiResponseMapper
import com.hassan.data.repositories.RatesRemoteDataSource
import com.hassan.data.repositories.RatesRemoteDataSourceImpl
import com.hassan.data.repositories.RatesRepositoryImpl
import com.hassan.domain.repositories.RatesRepository
import com.hassan.domain.usecases.ConvertRateUseCase
import com.hassan.domain.usecases.GetHistoricalRatesUseCase
import com.hassan.domain.usecases.GetRatesUseCase
import dagger.Binds
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
    fun providesRatesApi () : RatesApi {
        return NetworkModule().createRatesApi(LATEST_RATE_API_BASE_URL)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ResponseMapperModule {


    @Provides
    fun provideRatesResponseMapper(): RatesApiResponseMapper {
        return RatesApiResponseMapper()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RatesRemoteDataSourceModule {

    @Provides
    fun provideRatesRemoteDataSource(
        ratesApi: RatesApi,
        mapper: RatesApiResponseMapper
    ) : RatesRemoteDataSource {
        return RatesRemoteDataSourceImpl(ratesApi, mapper)
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
    fun provideConvertRateUseCase(
        ratesRepository: RatesRepository
    ) : ConvertRateUseCase {
        return ConvertRateUseCase(ratesRepository)
    }

    @ViewModelScoped
    @Provides
    fun providesGetHistoricalRatesUseCase(
        ratesRepository: RatesRepository
    ) : GetHistoricalRatesUseCase {
        return GetHistoricalRatesUseCase(ratesRepository)
    }
}