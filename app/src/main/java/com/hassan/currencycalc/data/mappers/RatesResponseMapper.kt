package com.hassan.currencycalc.data.mappers

import com.hassan.currencycalc.data.models.RatesResponse
import com.hassan.currencycalc.domain.entities.RatesResult

class RatesResponseMapper {
    //map response model to entity
    fun toRatesResult(response: RatesResponse): RatesResult {
        return RatesResult(
            success = response.success,
            timeSeries = response.timeSeries,
            startDate = response.startDate,
            endDate = response.endDate,
            base = response.base,
            rates = response.rates
        )
    }
}