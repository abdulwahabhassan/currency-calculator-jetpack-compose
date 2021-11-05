package com.hassan.currencycalc.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hassan.domain.usecases.ConvertRateUseCase
import com.hassan.domain.usecases.GetRatesUseCase

class MainViewModelFactory(
    private val getRatesUseCase: GetRatesUseCase,
    private val convertRateUseCase: ConvertRateUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getRatesUseCase, convertRateUseCase) as T

    }
}