package com.hassan.data.mappers

import com.hassan.data.api.RatesApiResponse
import com.hassan.domain.entities.LatestRates

class RatesApiResponseMapper {
    fun toLatestRates(response: RatesApiResponse): LatestRates {
        return LatestRates(
            response.success,
            response.timestamp,
            response.base,
            response.date,
            response.rates
        )
    }
}