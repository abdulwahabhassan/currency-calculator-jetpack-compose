package com.hassan.currencycalc.data.local.database.entities

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//define database entity
@Entity
data class RatesLocal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, //auto-generate primary key as id
    @ColumnInfo val success: Boolean? = false,
    @ColumnInfo(name = "time_series") val timeSeries: Boolean? = false,
    @ColumnInfo(name = "start_date") val startDate: String? = null,
    @ColumnInfo(name = "end_date") val endDate: String? = null,
    @ColumnInfo val base: String? = null,
    @ColumnInfo(name = "map_of_dates_to_rates") val mapOfDatesToRates: Map<String, Map<String, Double>>? = null,
    @ColumnInfo val target: List<String>? = arrayListOf()
)