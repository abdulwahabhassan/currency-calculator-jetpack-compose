package com.hassan.currencycalc.data.remote.mappers

import com.hassan.currencycalc.data.models.ErrorRemote
import com.hassan.currencycalc.utilities.BaseMapper
import com.hassan.currencycalc.data.models.RatesRemote
import com.hassan.currencycalc.domain.entities.Error
import com.hassan.currencycalc.domain.entities.RatesResult

class RatesRemoteMapper: BaseMapper<RatesRemote, RatesResult> {

    override fun mapToEntity(type: RatesRemote): RatesResult {
        return RatesResult(
            success = type.success,
            timeSeries = type.timeSeries,
            startDate = type.startDate,
            endDate = type.endDate,
            base = type.base,
            rates = type.rates,
            error = Error(type.errorRemote?.code, type.errorRemote?.info)
        )
    }

    override fun mapToDTO(type: RatesResult): RatesRemote {
        return RatesRemote(
            success = type.success,
            timeSeries = type.timeSeries,
            startDate = type.startDate,
            endDate = type.endDate,
            base = type.base,
            rates = type.rates,
            errorRemote = ErrorRemote(type.error?.code, type.error?.info)
        )
    }
}