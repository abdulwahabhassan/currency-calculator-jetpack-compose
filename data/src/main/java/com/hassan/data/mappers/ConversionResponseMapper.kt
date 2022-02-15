package com.hassan.data.mappers

import com.hassan.data.models.ConversionResponse
import com.hassan.domain.entities.ConversionResult

class ConversionResponseMapper {
    //map response model to entity
    fun toConversionResult(response: ConversionResponse): ConversionResult  {
        return ConversionResult(
            success = response.success,
            result = response.result
        )
    }
}