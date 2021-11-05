package com.hassan.domain.usecases

import com.hassan.domain.repositories.RatesRepository

class ConvertRateUseCase (private val repository: RatesRepository) {
    suspend operator fun invoke(symbol: String) = repository.convertRate(symbol)
}