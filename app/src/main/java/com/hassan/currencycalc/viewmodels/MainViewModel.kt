package com.hassan.currencycalc.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.hassan.domain.Result
import com.hassan.domain.entities.LatestRates
import com.hassan.domain.usecases.ConvertRateUseCase
import com.hassan.domain.usecases.GetRatesUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getRatesUseCases: GetRatesUseCase,
    private val convertRateUseCase: ConvertRateUseCase
) : ViewModel() {

    private val _remoteRates = MutableLiveData<LatestRates>()
    val remoteRates = _remoteRates

    private val _convertedRate = MutableLiveData<LatestRates>()
    val convertedRate = _convertedRate

    private val _error = MutableLiveData<String>(null)
    val error: LiveData<String> = _error

    fun getRates() {
        viewModelScope.launch {
            when (val ratesResult = getRatesUseCases.invoke()) {
                is Result.Success -> {
                    _remoteRates.postValue(ratesResult.data)
                }

                is Result.Error -> {
                    _error.postValue(ratesResult.exception.message)
                }
            }
        }
    }

    fun convertRate(symbols: String) {
        viewModelScope.launch {
            when (val ratesResult = convertRateUseCase.invoke(symbols)) {
                is Result.Success -> {
                    _convertedRate.postValue(ratesResult.data)
                }

                is Result.Error -> {
                    _error.postValue(ratesResult.exception.message)
                }
            }
        }
    }
}