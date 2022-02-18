package com.hassan.currencycalc.viewmodels

import androidx.lifecycle.*
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.Error
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
                //conversion result could be successful or erroneous depending on success or
                    //failure of the http network request and whether an exception was thrown when
                        //trying to perform the request or not
                is Result.Success -> { //if successful, retrieve the data
                    _conversionRate.postValue(conversionResult.data)
                }
                is Result.Error -> { //if erroneous, initialize a conversionResult that describes
                    //the error message of the failed http network request or the exception that was
                    //while trying to perform the network request
                    _conversionRate.postValue(
                        ConversionResult(error = Error(
                            902,
                            conversionResult.exception.localizedMessage)
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
                        _timeSeriesRates.postValue(timeSeriesResult.data)
                    }
                }
                is Result.Error -> { //if erroneous, initialize a conversionResult that describes
                    //the error message of the failed http network request or the exception that was
                    //while trying to perform the network request
                    _timeSeriesRates.postValue(
                        RatesResult(error = Error(
                            902,
                            timeSeriesResult.exception.localizedMessage)
                        )
                    )

                }
            }
        }
    }

}