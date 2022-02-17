package com.hassan.currencycalc.data.local.datasource

import com.hassan.currencycalc.data.local.database.dao.RatesLocalDao
import com.hassan.currencycalc.data.local.mappers.RatesLocalMapper
import com.hassan.currencycalc.domain.datasource.RatesLocalDataSource
import com.hassan.currencycalc.domain.Result
import com.hassan.currencycalc.domain.entities.RatesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatesLocalDataSourceImpl @Inject constructor(
    private val ratesLocalDao: RatesLocalDao,
    private val ratesLocalMapper: RatesLocalMapper
): RatesLocalDataSource {

    override suspend fun getRates(base: String, targetList: List<String>):
            Result<List<RatesResult>> = withContext(Dispatchers.IO) {
            return@withContext Result.Success(
                ratesLocalMapper.mapToEntitiesList(
                    ratesLocalDao.getRates(base, targetList)
                )
            )
    }

}
