package com.hassan.currencycalc

import android.app.Application
import com.hassan.currencycalc.di.ServiceLocator
import com.hassan.data.repositories.RatesRepositoryImpl
import com.hassan.domain.usecases.ConvertRateUseCase
import com.hassan.domain.usecases.GetHistoricalRatesUseCase
import com.hassan.domain.usecases.GetRatesUseCase

class App : Application() {
    private val ratesRepository: RatesRepositoryImpl
        get() = ServiceLocator.provideRatesRepository(this)

    val getRatesUseCase: GetRatesUseCase
        get() = GetRatesUseCase(ratesRepository)

    val convertRateUseCase: ConvertRateUseCase
        get() = ConvertRateUseCase(ratesRepository)

    val getHistoricalRatesUseCase: GetHistoricalRatesUseCase
        get() = GetHistoricalRatesUseCase(ratesRepository)
}