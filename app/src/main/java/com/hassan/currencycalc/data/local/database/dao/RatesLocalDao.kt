package com.hassan.currencycalc.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hassan.currencycalc.data.local.database.entities.RatesLocal

//data access object for Rates
@Dao
interface RatesLocalDao {
    //get rates whose base and target match the arguments
    @Query("SELECT * FROM ratesLocal WHERE target IN (:targetList) AND base LIKE :base")
    suspend fun getRates(base: String, targetList: List<String>): List<RatesLocal>

    //insert new rates, will return SQLite row-id of the newly inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(ratesLocal: RatesLocal): Long

    //update specified rates params with the corresponding arguments where the base and target params
    //of rates in the database match the base and targetList arguments, will return number of rows
    //affected by this query
    @Query("UPDATE ratesLocal SET start_date = (:startDate), end_date = (:endDate), map_of_dates_to_rates = (:mapOfDatesToRates) WHERE target IN (:targetList) AND base LIKE :base")
    suspend fun upDateRates(base: String, targetList: List<String>, startDate: String, endDate: String, mapOfDatesToRates:Map<String, Map<String, Double>>): Int


}