package com.hassan.currencycalc.data.local.mappers

import com.hassan.currencycalc.data.local.database.entities.RatesLocal
import com.hassan.currencycalc.utilities.BaseMapper
import com.hassan.currencycalc.domain.entities.RatesResult
import timber.log.Timber

class RatesLocalMapper: BaseMapper<RatesLocal, RatesResult> {
    override fun mapToEntity(type: RatesLocal): RatesResult {
        return RatesResult(
            type.success,
            type.timeSeries,
            type.startDate,
            type.endDate,
            type.base,
            type.mapOfDatesToRates
        )
    }

    override fun mapToDTO(type: RatesResult): RatesLocal {
       return RatesLocal(
           success = type.success,
           timeSeries = type.timeSeries,
           startDate = type.startDate,
           endDate = type.endDate,
           base = type.base,
           mapOfDatesToRates = type.rates,
           target = type.rates?.flatMap { date ->
               //transform each rate to it's key
               date.value.map { rate ->
                   Timber.d("$rate.key")
                   rate.key
               }
           }
       )
    }
}