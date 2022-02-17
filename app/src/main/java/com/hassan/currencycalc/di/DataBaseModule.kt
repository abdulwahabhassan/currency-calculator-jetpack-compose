package com.hassan.currencycalc.di

import android.content.Context
import androidx.room.Room
import com.hassan.currencycalc.data.local.database.AppDatabase
import com.hassan.currencycalc.data.local.database.Converters
import com.hassan.currencycalc.data.local.database.dao.RatesLocalDao
import com.hassan.currencycalc.utilities.Helpers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
        typeConverters: Converters
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Helpers.DATABASE_NAME
        )//addTypeConverter is used to provide converters which are used to convert complex custom
            //objects to primitive objects which room can store in the database. When the custom
                //objects are queried from the database, room will use these converters to converts
                //the objects from their primitive form  to their corresponding complex type
            .addTypeConverter(typeConverters)
            .build()
    }

    @Provides
    @Singleton
    fun provideRatesLocalDAO(appDatabase: AppDatabase): RatesLocalDao {
        return appDatabase.ratesLocalDao()
    }
}