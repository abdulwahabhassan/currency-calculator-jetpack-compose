package com.hassan.currencycalc.data.local.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject

//this annotation allows me to have control over this converter in cases such as when I
//want to use dependency injection to provide instances of this converter, by doing this I must
//manually provide this converter to room database builder using
//Room.databaseBuilder.addTypeConverter(Object)
@ProvidedTypeConverter
class Converters  @Inject constructor(
    var moshi: Moshi,
    private val mapAdapter: JsonAdapter<Map<String, Map<String, Double>>>,
//    private val listAdapter: JsonAdapter<List<String>>
    ){

    //convert seriesRates(String) to Map<String, Map<String, Double>>
    @TypeConverter
    fun fromSeriesRatesToMap(seriesRates: String): Map<String, Map<String, Double>>? {
        Timber.d("${mapAdapter.fromJson(seriesRates)}")
        return mapAdapter.fromJson(seriesRates)
    }

    //convert seriesRates(Map<String, Map<String, Double>>) to String
    @TypeConverter
    fun fromSeriesRatesToString(seriesRates: Map<String, Map<String, Double>>): String {
        Timber.d(mapAdapter.toJson(seriesRates))
        return mapAdapter.toJson(seriesRates)
    }

//    //convert target(String) to List<String>
//    @TypeConverter
//    fun fromTargetToList(target: String): List<String>? {
//        Timber.d("${listAdapter.fromJson(target)}")
//        return listAdapter.fromJson(target)
//    }
//
//    //convert target List<String> to String
//    @TypeConverter
//    fun fromTargetToString(target: List<String>): String {
//        Timber.d(listAdapter.toJson(target))
//        return listAdapter.toJson(target)
//    }

}