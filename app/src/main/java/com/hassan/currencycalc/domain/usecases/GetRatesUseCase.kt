package com.hassan.currencycalc.domain.usecases

import com.hassan.currencycalc.domain.repositories.RatesRepository

class GetRatesUseCase (private val ratesRepository: RatesRepository) {
    suspend operator fun invoke(
        base: String,
        target: String,
        startDate: String,
        endDate: String
    ) = ratesRepository.getRates(base, target, startDate, endDate)
}