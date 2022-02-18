package com.hassan.currencycalc.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesConnectivityManager(
        @ApplicationContext appContext: Context
    ): ConnectivityManager {
        return appContext.getSystemService(ConnectivityManager::class.java)
    }
}