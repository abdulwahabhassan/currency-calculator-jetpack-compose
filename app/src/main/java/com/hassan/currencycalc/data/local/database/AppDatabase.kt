package com.hassan.currencycalc.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hassan.currencycalc.utilities.Helpers.DATABASE_NAME
import com.hassan.currencycalc.data.local.database.dao.RatesLocalDao
import com.hassan.currencycalc.data.local.database.entities.RatesLocal


@Database(entities = [RatesLocal::class], version = 1) //entities refers to all entities(tables)
@TypeConverters(Converters::class) //specifies type converters room can use for complex objects
abstract class AppDatabase: RoomDatabase() {

    //data access object to use for querying a specific entity(row)
    abstract fun ratesLocalDao(): RatesLocalDao

}