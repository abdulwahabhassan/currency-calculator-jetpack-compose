package com.hassan.currencycalc

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hassan.domain.entities.Country
import com.madrapps.plot.line.DataPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.ZoneId
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

    //test dataPoints
    fun getLines(): List<List<DataPoint>> {
        return listOf(listOf(
            DataPoint(1f, 20f),
            DataPoint(2f, 50f),
            DataPoint(3f, 10f),
            DataPoint(4f, 0f),
            DataPoint(5f, -25f),
            DataPoint(6f, -75f),
            DataPoint(7f, -100f),
            DataPoint(8f, -80f),
            DataPoint(9f, -75f),
            DataPoint(10f, -55f),
            DataPoint(11f, -45f),
            DataPoint(12f, 50f),
            DataPoint(13f, 80f),
            DataPoint(14f, 70f),
            DataPoint(15f, 125f),
            DataPoint(16f, 200f),
            DataPoint(17f, 170f),
            DataPoint(18f, 135f),
            DataPoint(19f, 60f),
            DataPoint(20f, 20f),
            DataPoint(21f, 40f),
            DataPoint(22f, 75f),
            DataPoint(23f, 50f),
            DataPoint(24f, -80f),
            DataPoint(25f, -65f),
            DataPoint(26f, -46f),
            DataPoint(27f, -20f),
            DataPoint(28f, 40f),
            DataPoint(29f, 75f),
            DataPoint(30f, 50f)
        ),
        listOf(
            DataPoint(1f, 0f),
            DataPoint(2f, 1f),
            DataPoint(3f, 2f),
            DataPoint(4f, 3f),
            DataPoint(5f, 4f),
            DataPoint(6f, 5f),
            DataPoint(7f, 6f),
            DataPoint(8f, 7f),
            DataPoint(9f, 8f),
            DataPoint(10f, 9f),
            DataPoint(11f, 10f),
            DataPoint(12f, 11f),
            DataPoint(13f, 12f),
            DataPoint(14f, 13f),
            DataPoint(15f, 20f),
            DataPoint(16f, 25f),
            DataPoint(17f, 50f),
            DataPoint(18f, 25f),
            DataPoint(19f, 15f),
            DataPoint(20f, 10f),
            DataPoint(21f, -10f),
            DataPoint(22f, -20f),
            DataPoint(23f, -30f),
            DataPoint(24f, -80f),
            DataPoint(25f, -65f),
            DataPoint(26f, -46f),
            DataPoint(27f, -20f),
            DataPoint(28f, 10f),
            DataPoint(29f, 20f),
            DataPoint(30f, 30f),
            DataPoint(31f, 30f),
            DataPoint(32f, 30f),
            DataPoint(33f, 10f),
            DataPoint(34f, 0f),
            DataPoint(35f, -25f),
            DataPoint(36f, -75f),
            DataPoint(37f, -100f),
            DataPoint(38f, -80f),
            DataPoint(39f, -75f),
            DataPoint(40f, -55f),
            DataPoint(41f, -45f),
            DataPoint(42f, 50f),
            DataPoint(43f, 80f),
            DataPoint(44f, 40f),
            DataPoint(45f, 125f),
            DataPoint(46f, 200f),
            DataPoint(47f, 170f),
            DataPoint(48f, 135f),
            DataPoint(49f, 60f),
            DataPoint(50f, 20f),
            DataPoint(51f, 40f),
            DataPoint(52f, 75f),
            DataPoint(53f, 100f),
            DataPoint(54f, -80f),
            DataPoint(55f, -65f),
            DataPoint(56f, -46f),
            DataPoint(57f, -20f),
            DataPoint(58f, 40f),
            DataPoint(59f, 75f),
            DataPoint(60f, 50f),
            DataPoint(61f, 20f),
            DataPoint(62f, 50f),
            DataPoint(63f, 10f),
            DataPoint(64f, 0f),
            DataPoint(65f, -25f),
            DataPoint(66f, -75f),
            DataPoint(67f, -100f),
            DataPoint(68f, -80f),
            DataPoint(69f, -75f),
            DataPoint(70f, -55f),
            DataPoint(71f, -45f),
            DataPoint(72f, 50f),
            DataPoint(73f, 80f),
            DataPoint(74f, 70f),
            DataPoint(75f, 125f),
            DataPoint(76f, 200f),
            DataPoint(77f, 170f),
            DataPoint(78f, 135f),
            DataPoint(79f, 60f),
            DataPoint(80f, 20f),
            DataPoint(81f, 40f),
            DataPoint(82f, 75f),
            DataPoint(83f, 50f),
            DataPoint(84f, -80f),
            DataPoint(85f, -65f),
            DataPoint(86f, -46f),
            DataPoint(87f, -20f),
            DataPoint(88f, 40f),
            DataPoint(89f, 75f),
            DataPoint(90f, 50f)
            )
        )
    }


}


