package com.hassan.currencycalc.data.remote.mappers

import com.hassan.currencycalc.utilities.BaseMapper
import com.hassan.currencycalc.data.models.ConversionRemote
import com.hassan.currencycalc.data.models.ErrorRemote
import com.hassan.currencycalc.domain.entities.ConversionResult

class ConversionRemoteMapper : BaseMapper<ConversionRemote, ConversionResult> {

    override fun mapToEntity(type: ConversionRemote): ConversionResult {
        return ConversionResult(
            success = type.success,
            result = type.result
        )
    }

    override fun mapToDTO(type: ConversionResult): ConversionRemote {
        return ConversionRemote(
            success = type.success,
            result = type.result,
            errorRemote = ErrorRemote(type.error?.code, type.error?.info)
        )
    }
}