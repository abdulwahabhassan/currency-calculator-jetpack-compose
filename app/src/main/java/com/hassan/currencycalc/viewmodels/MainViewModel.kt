package com.hassan.currencycalc.viewmodels

import androidx.lifecycle.*
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.Error
import com.hassan.currencycalc.domain.entities.RatesResult
import com.hassan.currencycalc.domain.usecases.ConvertUseCase
import com.hassan.currencycalc.domain.usecases.GetRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getRatesUseCase: GetRatesUseCase,
    private val convertUseCase: ConvertUseCase
) : ViewModel() {

    //create a collectible public immutable state flow with a backing private mutable property which
    //the ui can collect from
    private val _timeSeriesRates: MutableStateFlow<RatesResult> = MutableStateFlow(RatesResult())
    val timeSeriesRates: StateFlow<RatesResult> = _timeSeriesRates

    private val _conversionRate: MutableStateFlow<ConversionResult> = MutableStateFlow(ConversionResult())
    val conversionRate: StateFlow<ConversionResult> = _conversionRate

    fun convert(base: String, target: String, amount: Double) {
        viewModelScope.launch {
            when (val conversionResult = convertUseCase.invoke(base = base, target, amount)) {
                //conversion result could be successful or erroneous depending on success or
                    //failure of the http network request and whether an exception was thrown when
                        //trying to perform the request or not
                is Result.Success -> { //if successful, set as new value
                    _conversionRate.value = conversionResult.data
                }
                is Result.Error -> { //if erroneous, initialize a conversionResult that describes
                    //the error message of the failed http network request or the exception that was
                    //while trying to perform the network request
                    _conversionRate.value = ConversionResult(
                        error = Error(
                            902,
                            conversionResult.exception.localizedMessage
                        )
                    )
                }
            }
        }
    }

    fun getTimeSeriesRates(base: String, target: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            when (val timeSeriesResult = getRatesUseCase.invoke(base, target, startDate, endDate)) {
                //rates result could be successful or erroneous depending on success or
                //failure of the http network request and whether an exception was thrown when
                //trying to perform the request or not
                is Result.Success -> {//if successful, retrieve the data
                    if (timeSeriesResult.data.success == true) {
                        _timeSeriesRates.value = timeSeriesResult.data
                    }
                }
                is Result.Error -> { //if erroneous, initialize a conversionResult that describes
                    //the error message of the failed http network request or the exception that was
                    //while trying to perform the network request
                    _timeSeriesRates.value = RatesResult(
                        error = Error(
                            902,
                            timeSeriesResult.exception.localizedMessage
                        )
                    )
                }
            }
        }
    }

}