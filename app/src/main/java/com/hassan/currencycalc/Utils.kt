package com.hassan.currencycalc

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hassan.currencycalc.domain.entities.Country
import com.hassan.currencycalc.domain.entities.RatesResult
import com.madrapps.plot.line.DataPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

object Utils {

    val DATABASE_NAME = "currency_calc"

    //encode image to base64 string
    fun encodeImageToBase64String(res: Resources, imageResId: Int): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeResource(res, imageResId)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val imageString: String = Base64.getEncoder().encodeToString(imageBytes)
        Log.d("encodeImage", imageString)
        return imageString
    }

    //read json file from asset and return list of countries with data of their flag and currency
    //symbol
    private fun loadCountriesFromAsset(assets: AssetManager): List<Country>? {

        val listOfCountries: List<Country>?

        try {
            //use InputStream to open the file and stream the data into it.
            val stream = assets.open("countries.json")
            //create a variable to store the size of the file.
            val size = stream.available()
            //create a buffer of the size of the file.
            val buffer = ByteArray(size)
            //read the inputStream file into the buffer.
            stream.read(buffer)
            //close the inputStream file.
            stream.close()
            //convert the buffer file to the format in which you need your data.
            val stringJson = String(buffer, charset("UTF-8"))
            val gson = Gson()
            val customListType = object : TypeToken<List<Country>>() {}.type //custom list type
            listOfCountries = gson.fromJson(stringJson, customListType)

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return listOfCountries
    }

    //Decode base64 string to image bitmap
    fun getFlagImageBitMap(base64String: String): ImageBitmap {
        var encodedString = base64String
        //remove the image description part of the string
        if(encodedString.contains(",")) {
            encodedString = encodedString.split(",")[1]
        }
        val decodedByteArray = Base64.getDecoder()
            .decode(encodedString.toByteArray(charset("UTF-8")))
        val bitMap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        return bitMap.asImageBitmap()
    }

    @SuppressLint("SimpleDateFormat")
    //Get date for number of days ago
    fun calculatePastDate(daysAgo: Long): String {
        //get zone id of specific region
        val zoneId = ZoneId.of("Africa/Lagos")
        //get today's date
        val today = LocalDate.now(zoneId)
        //subtract number of days ago and return as string
        return today.minusDays(daysAgo).toString()
    }

    //get today's date
    fun todayDate(): String {
        val zoneId = ZoneId.of("Africa/Lagos")
        return LocalDate.now(zoneId).toString()
    }

    //returns a map of each country's currency code to their flag
    fun loadMapOfCurrencySymbolToFlag(assets: AssetManager): MutableMap<String, String> {
        //create an empty map
        val mapOfCurrencySymbolToFlag = mutableMapOf<String, String>()
        //load countries from asset, store their currency code and flag as a key-value pair in the
        //map
        loadCountriesFromAsset(assets)?.forEach {
            mapOfCurrencySymbolToFlag[it.currency.code] = it.flag
        }
        return mapOfCurrencySymbolToFlag
    }

    fun mapDataPoints(timeSeriesRates: RatesResult?, target: String): List<DataPoint>? {
        return if (timeSeriesRates?.rates != null) {
            val mapOfDataPoints = timeSeriesRates.rates?.map { rate ->
                //remove non-word characters
                val dateValue = DecimalFormat("0000")
                    .format(rate.key.replace(Regex("\\W"), "")
                        .takeLast(6).toFloat()).toFloat()
                Log.d("yes", dateValue.toString())
                val rateValue = rate.value[target]?.toFloat() ?: 0f
                DataPoint(dateValue, rateValue)
            }
            mapOfDataPoints
        } else {
            null
        }
    }

    fun calculateMonth(value: Int): String? {
        val map = mapOf<Int, String>(
            1 to "Jan",
            2 to "Feb",
            3 to "Mar",
            4 to "Apr",
            5 to "May",
            6 to "Jun",
            7 to "Jul",
            8 to "Aug",
            9 to "Sep",
            10 to "Oct",
            11 to "Nov",
            12 to "Dec"
        )
        return map[value.toInt()]
    }

}


