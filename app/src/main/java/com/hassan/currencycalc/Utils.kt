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
import com.hassan.domain.entities.Country
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

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

    //read json file from asset and return list of countries with data of their flag and currency symbol
   fun loadCountriesFromAsset(assets: AssetManager, jsonFileName: String): List<Country>? {

        val listOfCountries: List<Country>?

        try {
            //use InputStream to open the file and stream the data into it.
            val stream = assets.open(jsonFileName)
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
        if(encodedString.contains(",")) {
            encodedString = encodedString.split(",")[1]
        }
        val decodedByteArray = Base64.getDecoder()
            .decode(encodedString.toByteArray(charset("UTF-8")))
        val bitMap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        return bitMap.asImageBitmap()
    }

    @SuppressLint("SimpleDateFormat")
    fun calculatePastDate(daysAgo: Int): String {
        //get current time and format it
        val currentDate = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time).toLong()
        var pastDate = (currentDate - daysAgo).toString()
        while (pastDate.length < 8) {
            pastDate += "0"
        }
        return pastDate
    }
}