package com.hassan.currencycalc.ui.views

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hassan.currencycalc.App
import com.hassan.currencycalc.ui.theme.CurrencyCalcTheme
import com.hassan.currencycalc.ui.theme.RippleCustomTheme
import com.hassan.currencycalc.viewmodels.MainViewModel
import com.hassan.currencycalc.viewmodels.MainViewModelFactory
import com.hassan.domain.entities.Country
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (this.application as App).getRatesUseCase,
            (this.application as App).convertRateUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            CurrencyCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val mapOfCurrencySymbolsToFlag = mutableMapOf<String, String>()
                    loadJsonFromAsset()?.forEach {
                        mapOfCurrencySymbolsToFlag[it.currency.code] = it.flag
                    }
                    MainActivityScreen(viewModel = mainViewModel, mapOfCurrencySymbolsToFlag)
                }
            }
        }

//        mainViewModel.getRates()
//        mainViewModel.remoteRates.observe(this, {
//            Log.i("remote rates", "$it")
//        }
//        )
//
//        mainViewModel.convertRate("PLN")
//        mainViewModel.convertedRate.observe(this, {
//            Log.i("remote rates converted", "$it")
//        }
//        )

    }

    private fun loadJsonFromAsset(): List<Country>? {

        val listOfCountries: List<Country>?

        try {
            //use InputStream to open the file and stream the data into it.
            val stream = assets.open("flags.json")
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

        Log.d("JsonTester", "$listOfCountries")
        return listOfCountries
    }
}


@Composable
fun MainActivityScreen(viewModel: ViewModel, mapOfCurrencySymbolsToFlag: MutableMap<String, String>) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                elevation = 0.dp,
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Navigation menu icon",
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    }
                },
                actions = {
                    CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
                        TextButton(
                            onClick = {
                                /*TODO*/
                            },
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Sign up",
                                color = MaterialTheme.colors.primaryVariant,
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding -> BodyContent(Modifier.padding(innerPadding), mapOfCurrencySymbolsToFlag) }
}

@Composable
fun BodyContent(modifier: Modifier, mapOfCurrencySymbolsToFlag: MutableMap<String, String>) {
    val scrollState = rememberScrollState()
    var trailingText by rememberSaveable { mutableStateOf("PLN")}

    Column(modifier = modifier.verticalScroll(scrollState)) {

        Column(modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp)) {
            AppName(modifier)
            Spacer(modifier = Modifier.height(40.dp))
            //Make the first EditText readOnly
            EditText("EUR", modifier, readOnly = true, defaultValue = "1", enabled = false)
            Spacer(modifier = Modifier.height(16.dp))
            EditText( trailingText, modifier, readOnly = false, defaultValue = "", enabled = false)
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                //disable the first DropDownEditText and make readOnly
                DropDownEditText(
                    modifier,
                    readOnly = true,
                    enabled = false,
                    defaultSymbol = "NGN",
                    mapOfCurrencySymbolsToFlag
                )
                Icon(
                    imageVector = Icons.Filled.CompareArrows,
                    contentDescription = "Compare arrow",
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colors.onSecondary
                )
                DropDownEditText(
                    modifier,
                    readOnly = false,
                    enabled = true,
                    defaultSymbol = "PLN",
                    mapOfCurrencySymbolsToFlag
                )

            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                elevation = null,
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.primary
                )
            ) {

                Text(text = "Convert", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
                TextButton(
                    onClick = {  },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Mid-market exchange rate at 13:38 UTC",
                        color = MaterialTheme.colors.onPrimary,
                        textDecoration = TextDecoration.Underline
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Info icon",
                        modifier = Modifier.clip(CircleShape),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(32.dp))
        GraphSection()
    }

}

@Composable
fun GraphSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {
        Spacer(modifier = Modifier.height(420.dp))
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = "Get rate alerts straight to your email inbox",
                color = MaterialTheme.colors.primary,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun DropDownEditText(
    modifier: Modifier,
    readOnly: Boolean,
    enabled: Boolean,
    defaultSymbol: String,
    mapOfCurrencySymbolsToFlag: MutableMap<String, String>
) {

    var textFieldSize by remember { mutableStateOf(Size.Zero)}
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedSymbol by rememberSaveable {mutableStateOf(defaultSymbol)}
//    val flagKey = selectedSymbol.substring(0, 2).lowercase(Locale.getDefault())

    Box {
        OutlinedTextField(
            modifier = modifier
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colors.secondaryVariant,
                    shape = RoundedCornerShape(6.dp)
                )
                .width(150.dp)
                .onGloballyPositioned { coordinates ->
                    //This allows the dropdown menu to take the same width as the OutlinedTextField
                    textFieldSize = coordinates.size.toSize()
                },
            readOnly = readOnly,
            value = selectedSymbol,
            leadingIcon = {
                val base64String = mapOfCurrencySymbolsToFlag[selectedSymbol]
                    if (base64String != null) {
                        Icon(
                            bitmap = getFlagImageBitMap(base64String),
                            contentDescription = "Flag",
                            tint = Color.Unspecified
                        )
                    }
            },
            textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold),
            singleLine = true,
            onValueChange = { newInput ->
                selectedSymbol = newInput
            },
            trailingIcon = {
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) "Show less" else "Show more",
                    Modifier
                        .clip(CircleShape)
                        .clickable(enabled = enabled) { isExpanded = !isExpanded },
                    tint = MaterialTheme.colors.onSecondary
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onSecondary,
                textColor = Color.DarkGray
            ),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = modifier
                    //This is where I used the coordinates from the OutlinedTextField to specify the
                    //width of the DropDrownMenu to be the same as the OutlinedTextField
                .width(with(LocalDensity.current){textFieldSize.width.toDp()}),
        ) {
            mapOfCurrencySymbolsToFlag.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedSymbol = item.key
                        isExpanded = false
                    }
                ) {
                    Icon(
                        bitmap = getFlagImageBitMap(item.value),
                        contentDescription ="Flag",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item.key, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }
            }
        }
    }
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

@Composable
fun EditText(
    trailingText: String,
    modifier: Modifier,
    readOnly: Boolean,
    defaultValue: String,
    enabled: Boolean
) {
    var value by rememberSaveable { mutableStateOf(defaultValue)}

    TextField(
        value = value,
        readOnly = readOnly,
        enabled = enabled,
        onValueChange = {},
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        trailingIcon = {
            Text(
                text = trailingText,
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold
            )
        },
        shape = RoundedCornerShape(6.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.DarkGray,
            backgroundColor = MaterialTheme.colors.secondary,
            disabledTextColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onSecondary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}


@Composable
fun AppName(modifier: Modifier) {
    Spacer(modifier = modifier.height(20.dp))
    Text(
        text = "Currency",
        color = MaterialTheme.colors.onPrimary,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        modifier = modifier.fillMaxWidth()
    )
    Row(Modifier.fillMaxWidth()) {
        Text(
            text = "Calculator",
            color = MaterialTheme.colors.onPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = ".",
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
        )
    }
}
