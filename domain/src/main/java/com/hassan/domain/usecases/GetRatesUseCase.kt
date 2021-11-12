package com.hassan.domain.usecases

import com.hassan.domain.repositories.RatesRepository

class GetRatesUseCase (private val ratesRepository: RatesRepository) {
    suspend operator fun invoke(base: String) = ratesRepository.getRemoteRates(base)
}