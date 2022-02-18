package com.hassan.currencycalc.di

import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoshiModule {

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesConverterFactory(
        moshi: Moshi
    ): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun providesRatesMapAdapter(
        moshi: Moshi
    ): JsonAdapter<Map<String, Map<String, Double>>> {
        //create new type literal
        val mapType: Type = object : TypeToken<Map<String, Map<String, Double>>>() {}.type
        //create jsonAdapter for new type
        return moshi.adapter(mapType)

    }

//    @Singleton
//    @Provides
//    fun providesTargetListAdapter(
//        moshi: Moshi
//    ): JsonAdapter<List<String>> {
//        //create new type literal
//        val listType: Type = object : TypeToken<List<String>>() {}.type
//        //create jsonAdapter for new type
//        return moshi.adapter(listType)
//
//    }
}