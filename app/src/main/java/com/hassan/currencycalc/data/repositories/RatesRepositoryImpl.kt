package com.hassan.currencycalc.data.repositories

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.os.Build
import androidx.annotation.RequiresApi
import com.hassan.currencycalc.domain.datasource.RatesLocalDataSource
import com.hassan.currencycalc.domain.datasource.RatesRemoteDataSource
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.ConversionResult
import com.hassan.currencycalc.domain.entities.Error
import com.hassan.currencycalc.domain.entities.RatesResult
import com.hassan.currencycalc.domain.repositories.RatesRepository
import timber.log.Timber
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val ratesRemoteDataSource: RatesRemoteDataSource,
    private val ratesLocalDataSource: RatesLocalDataSource,
    private val connectivityManager: ConnectivityManager
) : RatesRepository {


    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun getRates(
        base: String,
        target: String,
        startDate: String,
        endDate: String
    ): Result<RatesResult> {
        //verify network capability. if network is active and capable of connecting to the internet,
        //fetch from remote (remote data source) else fetch from database (local data source)
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return if (networkCapabilities?.hasCapability(NET_CAPABILITY_VALIDATED) == true) {
            ratesRemoteDataSource.getRates(base, target, startDate, endDate)
        } else {
            ratesLocalDataSource.getRates(base, target)
        }

    }

    override suspend fun convert(
        from: String,
        to: String,
        amount: Double
    ): Result<ConversionResult> {
        //verify network capability. if network is active and capable of connecting to the internet,
        //convert from remote (remote data source) else return a success result wrapped around a
        //conversion result with an error describing that network is unable to perform conversion
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return if (networkCapabilities?.hasCapability(NET_CAPABILITY_VALIDATED) == true){
            ratesRemoteDataSource.convert(from, to, amount)
        } else {
            Result.Success(
                ConversionResult(
                    error = Error(901, info = "No Network, Can't perform conversion")
                )
            )
        }
    }


}