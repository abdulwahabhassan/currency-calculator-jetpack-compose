package com.hassan.domain.usecases

import com.hassan.domain.repositories.RatesRepository

class GetHistoricalRatesUseCase(private val ratesRepository: RatesRepository) {
    suspend operator fun invoke(date: String, base: String, symbols: String) =
        ratesRepository.getHistoricalRemoteRates(date, base, symbols)
}