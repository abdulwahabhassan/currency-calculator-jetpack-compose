package com.hassan.data.mappers

import com.hassan.data.api.RatesApiResponse
import com.hassan.domain.entities.Rates

class RatesApiResponseMapper {
    fun toRates(response: RatesApiResponse): Rates {
        return Rates(
            response.success,
            response.timestamp,
            response.historical,
            response.base,
            response.date,
            response.rates
        )
    }
}