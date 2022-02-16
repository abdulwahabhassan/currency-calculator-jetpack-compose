package com.hassan.currencycalc.database.entities

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//define database entity and it's access object
@Entity
data class Rates(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, //auto-generate primary key as id
    @ColumnInfo val success: Boolean? = false,
    @ColumnInfo(name = "time_series") val timeSeries: Boolean? = false,
    @ColumnInfo(name = "start_date") val startDate: String? = null,
    @ColumnInfo(name = "end_date") val endDate: String? = null,
    @ColumnInfo val base: String? = null,
    @ColumnInfo(name = "map_of_dates_to_rates") val mapOfDatesToRates: Map<String, Map<String, Double>>? = null,
    @ColumnInfo val target: List<String>? = arrayListOf()
)

//data access object for Rates
@Dao
interface RatesDao {
    //get rates whose base and target match the arguments
    @Query("SELECT * FROM rates WHERE target IN (:targetList) AND base LIKE :base")
    suspend fun getRates(base: String, targetList: List<String>): List<Rates>

    //insert new rates, will return SQLite row-id of the newly inserted row
    @Insert(onConflict = REPLACE)
    suspend fun insertRates(rates: Rates): Long

    //update specified rates params with the corresponding arguments where the base and target params
    //of rates in the database match the base and targetList arguments, will return number of rows
    //affected by this query
    @Query("UPDATE rates SET start_date = (:startDate), end_date = (:endDate), map_of_dates_to_rates = (:mapOfDatesToRates) WHERE target IN (:targetList) AND base LIKE :base")
    suspend fun upDateRates(base: String, targetList: List<String>, startDate: String, endDate: String, mapOfDatesToRates:Map<String, Map<String, Double>>): Int


}