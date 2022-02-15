package com.hassan.domain.usecases

import com.hassan.domain.repositories.RatesRepository

class ConvertUseCase(private val ratesRepository: RatesRepository) {
    suspend operator fun invoke(
        base: String,
        target: String,
        amount: Double
    ) = ratesRepository.convert(base, target, amount)
}