package com.hassan.data.mappers

import com.hassan.data.models.RatesResponse
import com.hassan.domain.entities.RatesResult

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