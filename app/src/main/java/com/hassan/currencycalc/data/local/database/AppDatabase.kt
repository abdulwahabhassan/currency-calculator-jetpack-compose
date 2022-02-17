package com.hassan.currencycalc.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hassan.currencycalc.utilities.Helpers.DATABASE_NAME
import com.hassan.currencycalc.data.local.database.dao.RatesLocalDao
import com.hassan.currencycalc.data.local.database.entities.RatesLocal


@Database(entities = [RatesLocal::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun ratesLocalDao(): RatesLocalDao

}