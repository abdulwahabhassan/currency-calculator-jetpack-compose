package com.hassan.currencycalc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //plant timber logger
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}