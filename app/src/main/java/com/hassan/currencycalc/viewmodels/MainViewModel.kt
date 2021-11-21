package com.hassan.currencycalc.viewmodels

import androidx.lifecycle.*
import com.hassan.domain.Result
import com.hassan.domain.entities.Rates
import com.hassan.domain.usecases.ConvertRateUseCase
import com.hassan.domain.usecases.GetHistoricalRatesUseCase
import com.hassan.domain.usecases.GetRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getRatesUseCase: GetRatesUseCase,
    private val convertRateUseCase: ConvertRateUseCase,
    private val getHistoricalRatesUseCase: GetHistoricalRatesUseCase
) : ViewModel() {

    private val _remoteRates = MutableLiveData<Rates>()
    val remoteRates = _remoteRates

    private val _convertedRate = MutableLiveData<Rates>()
    val convertedRate = _convertedRate

    private val _historicalRates = MutableLiveData<Rates>()
    val historicalRates = _historicalRates

    private val _error = MutableLiveData<String>(null)
    val error: LiveData<String> = _error

    fun getRates(base: String) {
        viewModelScope.launch {
            when (val ratesResult = getRatesUseCase.invoke(base)) {
                is Result.Success -> {
                    _remoteRates.postValue(ratesResult.data)
                }

                is Result.Error -> {
                    _error.postValue(ratesResult.exception.message)
                }
            }
        }
    }

    fun convertRate(base: String, symbols: String) {
        viewModelScope.launch {
            when (val ratesResult = convertRateUseCase.invoke(base, symbols)) {
                is Result.Success -> {
                    _convertedRate.postValue(ratesResult.data)
                }

                is Result.Error -> {
                    _error.postValue(ratesResult.exception.message)
                }
            }
        }
    }

    fun getHistoricalRates(date: String, base: String, symbols: String) {
        viewModelScope.launch {
            when (val ratesResult = getHistoricalRatesUseCase.invoke(date, base, symbols)) {
                is Result.Success -> {
                    _historicalRates.postValue(ratesResult.data)

                }

                is Result.Error -> {
                    _error.postValue(ratesResult.exception.message)
                }
            }
        }
    }
}