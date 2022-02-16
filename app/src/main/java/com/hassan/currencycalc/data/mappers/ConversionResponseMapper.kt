package com.hassan.currencycalc.data.mappers

import com.hassan.currencycalc.data.models.ConversionResponse
import com.hassan.currencycalc.domain.entities.ConversionResult

class ConversionResponseMapper {
    //map response model to entity
    fun toConversionResult(response: ConversionResponse): ConversionResult {
        return ConversionResult(
            success = response.success,
            result = response.result
        )
    }
}