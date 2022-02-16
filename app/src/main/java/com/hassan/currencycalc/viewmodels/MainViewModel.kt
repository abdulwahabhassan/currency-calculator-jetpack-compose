package com.hassan.currencycalc.viewmodels

import androidx.lifecycle.*
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.RatesResult
import com.hassan.currencycalc.domain.usecases.ConvertUseCase
import com.hassan.currencycalc.domain.usecases.GetRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getRatesUseCase: GetRatesUseCase,
    private val convertUseCase: ConvertUseCase
) : ViewModel() {

    private val _timeSeriesRates = MutableLiveData<RatesResult>()
    val timeSeriesRates = _timeSeriesRates

    private val _conversionRate = MutableLiveData<ConversionResult>()
    val conversionRate = _conversionRate

    fun convert(base: String, target: String, amount: Double) {
        viewModelScope.launch {
            when (val conversionResult = convertUseCase.invoke(base = base, target, amount)) {
                is Result.Success -> {
                    if (conversionResult.data.success == true) {
                        _conversionRate.postValue(conversionResult.data)
                    }
                }
            }
        }
    }

    fun getTimeSeriesRates(base: String, target: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            when (val timeSeriesResult = getRatesUseCase.invoke(base, target, startDate, endDate)) {
                is Result.Success -> {
                    if (timeSeriesResult.data.success == true) {
                        _timeSeriesRates.postValue(timeSeriesResult.data)
                    }
                }
            }
        }
    }

}