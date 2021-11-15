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


}

val RoundRectangle: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline.Rounded {
        val radius = 8.dp.value * density.density
        return Outline.Rounded(RoundRect(size.toRect(), CornerRadius(radius, radius)))
    }

    override fun toString(): String = "RoundRectangleShape"
}

internal fun Dp.toPx(density: Density) = value * density.density