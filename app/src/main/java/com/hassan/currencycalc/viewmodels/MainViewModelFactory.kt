package com.hassan.currencycalc.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hassan.domain.usecases.GetRatesUseCase

class MainViewModelFactory(
    private val getRatesUseCase: GetRatesUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getRatesUseCase) as T

    }
}