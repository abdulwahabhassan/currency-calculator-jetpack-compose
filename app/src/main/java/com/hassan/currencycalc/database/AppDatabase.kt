package com.hassan.currencycalc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hassan.currencycalc.Utils.DATABASE_NAME
import com.hassan.currencycalc.database.entities.Rates
import com.hassan.currencycalc.database.entities.RatesDao

@Database(entities = [Rates::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun RatesDao(): RatesDao

    companion object{
        @Volatile private var instance: AppDatabase? = null

        //retrieve a single instance of app database
        fun getInstance(context: Context): AppDatabase {
            //return a new instance only if an instance doesn't already exist
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}