package com.hassan.currencycalc.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.hassan.domain.Result
import com.hassan.domain.entities.LatestRates
import com.hassan.domain.usecases.GetRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getRatesUseCases: GetRatesUseCase
) : ViewModel() {

    private val _remoteRates = MutableLiveData<LatestRates>()
    val remoteRates = _remoteRates

    private val _error = MutableLiveData<String>(null)
    val error: LiveData<String> = _error

    fun getRates() {
        viewModelScope.launch {
            when (val ratesResult = getRatesUseCases.invoke()) {
                is Result.Success -> {
                    remoteRates.postValue(ratesResult.data)
                }

                is Result.Error -> {
                    _error.postValue(ratesResult.exception.message)
                    Log.d("remote rates", "fail ${ratesResult.exception.message}")
                }
            }
        }
    }
}