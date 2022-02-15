package com.hassan.data.mappers

import com.hassan.data.models.RatesResponse
import com.hassan.domain.entities.RatesResult

class RatesResponseMapper {
    fun toRates(response: RatesResponse): RatesResult {
        return RatesResult(
            response.success,
            response.timestamp,
            response.historical,
            response.base,
            response.date,
            response.rates
        )
    }
}