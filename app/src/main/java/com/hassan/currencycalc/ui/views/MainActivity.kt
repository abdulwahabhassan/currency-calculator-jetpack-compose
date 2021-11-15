package com.hassan.currencycalc.ui.views

import android.os.Bundle
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.hassan.currencycalc.App
import com.hassan.currencycalc.RoundRectangle
import com.hassan.currencycalc.Utils
import com.hassan.currencycalc.toPx
import com.hassan.currencycalc.ui.theme.CurrencyCalcTheme
import com.hassan.currencycalc.ui.theme.RippleCustomTheme
import com.hassan.currencycalc.viewmodels.MainViewModel
import com.hassan.currencycalc.viewmodels.MainViewModelFactory
import com.hassan.domain.entities.Rates
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (this.application as App).getRatesUseCase,
            (this.application as App).convertRateUseCase,
            (this.application as App).getHistoricalRatesUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val mapOfCurrencySymbolsToFlag = mutableMapOf<String, String>()
                    Utils.loadCountriesFromAsset(assets, "flags.json")?.forEach {
                        mapOfCurrencySymbolsToFlag[it.currency.code] = it.flag
                    }
                    MainActivityScreen(mainViewModel = mainViewModel, mapOfCurrencySymbolsToFlag)
                }
            }
        }

//        mainViewModel.getRates()
//        mainViewModel.remoteRates.observe(this, {
//            Log.i("remote rates", "$it")
//        }
//        )
    }
}

@Composable
fun MainActivityScreen(
    mainViewModel: MainViewModel,
    mapOfCurrencySymbolsToFlag: MutableMap<String, String>
) {

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
    ) { innerPadding ->
        BodyContent(
            Modifier.padding(innerPadding),
            mapOfCurrencySymbolsToFlag,
            mainViewModel
        )
    }
}

@Composable
fun BodyContent(
    modifier: Modifier,
    mapOfCurrencySymbolsToFlag: MutableMap<String, String>,
    mainViewModel: MainViewModel
) {
    val scrollState = rememberScrollState()
    var firstEditTextTrailingText by rememberSaveable { mutableStateOf("EUR")}
    var secondEditTextTrailingText by rememberSaveable { mutableStateOf("PLN")}
    val convertedRate by mainViewModel.convertedRate.observeAsState(Rates())

    Column(modifier = modifier.verticalScroll(scrollState)) {

        Column(modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp)) {
            AppName(modifier)
            Spacer(modifier = Modifier.height(40.dp))
            //Make the first EditText readOnly
            EditText(
                firstEditTextTrailingText,
                modifier,
                readOnly = true,
                value = "1",
                enabled = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditText(
                secondEditTextTrailingText,
                modifier,
                readOnly = false,
                value = if (convertedRate.rates?.get(secondEditTextTrailingText) != null)
                    convertedRate.rates?.get(secondEditTextTrailingText).toString() else "" ,
                enabled = false
            )
            Spacer(modifier = Modifier.height(32.dp))

            //drop down edit texts row
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
                    defaultSymbol = "EUR",
                    mapOfCurrencySymbolsToFlag,
                    onSymbolSelected = { newText -> firstEditTextTrailingText = newText }
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
                    mapOfCurrencySymbolsToFlag,
                    onSymbolSelected = { newText -> secondEditTextTrailingText = newText }
                )

            }
            Spacer(modifier = Modifier.height(32.dp))

            //covert button
            Button(
                elevation = null,
                onClick = {
                    mainViewModel.convertRate(firstEditTextTrailingText, secondEditTextTrailingText)
                },
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

            //mid market exchage rate text
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
        GraphSection(mainViewModel, firstEditTextTrailingText, secondEditTextTrailingText)
    }

}


@Composable
fun GraphSection(mainViewModel: MainViewModel, base: String, symbols: String) {

    val date by rememberSaveable { mutableStateOf("2019-03-16") }
    mainViewModel.getHistoricalRates(date, base, symbols)
    val rates by mainViewModel.historicalRates.observeAsState(Rates())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {

//        Text(text = "$rates", color = Color.White)
        HistoricalRatesGraph(lines = listOf(listOf(
            DataPoint(0f, 1f),
            DataPoint(1f, 2f),
            DataPoint(2f, 3f),
            DataPoint(3f, 4f),
            DataPoint(4f, 4f),
            DataPoint(5f, 1f),
            DataPoint(6f, 4f),
            DataPoint(7f, 4f),
            DataPoint(8f, 4f),
            DataPoint(9f, 4f),
            DataPoint(10f, 2f),
            DataPoint(11f, 3f),
            DataPoint(12f, 4f),
            DataPoint(13f, 4f),
            DataPoint(14f, 1f),
            DataPoint(15f, 4f),
            DataPoint(16f, 4f),
            DataPoint(17f, 1f)
                )
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(
            onClick = {  },
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
fun HistoricalRatesGraph(lines: List<List<DataPoint>>,  modifier: Modifier = Modifier) {
    val totalWidth = remember { mutableStateOf(0) }

    Column(Modifier.onGloballyPositioned {
        totalWidth.value = it.size.width
    }) {
        val cardWidth = remember { mutableStateOf(0) }
        val greenColor = MaterialTheme.colors.primaryVariant
        val xOffset = remember { mutableStateOf(0f) }
        val visibility = remember { mutableStateOf(false) }
        val points = remember { mutableStateOf(listOf<DataPoint>()) }
        val localDensity = LocalDensity.current
        val padding = 16.dp

        Box(Modifier.height(100.dp)) {
            if (visibility.value) {
                Surface(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.Center)
                        .onGloballyPositioned {
                            cardWidth.value = it.size.width
                        }
                        .graphicsLayer(translationX = xOffset.value),
                    shape = RoundedCornerShape(8.dp),
                    color = greenColor
                ) {
                    Column(
                        Modifier
                            .padding(8.dp)
                    ) {
                        val value = points.value

                        if (value.isNotEmpty()) {
                            val x = DecimalFormat("#.#").format(value[0].x)
                            val y = DecimalFormat("#.#").format(value[0].y)

                            Text(
                                text = "$x Jun",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White
                            )
                            Text(
                                text = "1 Eur = $y",
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )
                        }
                    }
                }

            }
        }

        MaterialTheme(colors = MaterialTheme.colors.copy(surface = MaterialTheme.colors.onPrimary)) {
            LineGraph(
                plot = LinePlot(
                    listOf(
                        LinePlot.Line(
                            dataPoints = lines[0],
                            connection = null,
                            intersection = null,
                            highlight = LinePlot.Highlight { center ->
                                drawCircle(Color.White, 5.dp.toPx(), center)
                                drawCircle(greenColor, 3.dp.toPx(), center)
                            },
                            areaUnderLine = LinePlot.AreaUnderLine(color = MaterialTheme.colors.primary, alpha = 0.5f)
                        )
                    ),
                    xAxis = LinePlot.XAxis(steps = 8, stepSize = 15.dp, paddingTop = 0.dp) {
                            min, offset, max ->
                        for (it in 0 until 8) {
                            val value = it * offset + min
                            Column {
                                val isMajor = value % 2 == 0f
                                Canvas(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .height(20.dp).size(4.dp),
                                    onDraw = {
                                        drawRect(
                                            color = Color.White,
                                            alpha = 1f,
                                            size = Size(4f, 8f)
                                        )
                                    })
                                if (isMajor) {
                                    Text(
                                        text = DecimalFormat("#.# Jun").format(value),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.caption,
                                        color = Color.White
                                    )
                                }
                            }
                            if (value > 31) {
                                break
                            }
                        }

                    },
                    yAxis = LinePlot.YAxis(steps = 7, paddingEnd = 0.dp) { min, offset, max ->
                        for (it in 0 until 7) {
                            val value = it + min
                            Column {
                                Canvas(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .height(20.dp).size(4.dp),
                                    onDraw = {
                                        drawRect(
                                            color = Color.White,
                                            alpha = 1f,
                                            size = Size(8f, 4f)
                                        )
                                    })
                            }
                            if (value > max) {
                                break
                            }
                        }
                    }
                ),
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 24.dp, 8.dp)
                    .height(300.dp),
                onSelectionStart = { visibility.value = true },
                onSelectionEnd = { visibility.value = false }
            ) { x, pts ->
                val cWidth = cardWidth.value.toFloat()
                var xStart = x
                xStart = when {
                    xStart + cWidth > totalWidth.value -> totalWidth.value - cWidth - 24f
                    else -> xStart
                }
                xOffset.value = xStart
                points.value = pts
            }
        }

    }


}


@Composable
fun DropDownEditText(
    modifier: Modifier,
    readOnly: Boolean,
    enabled: Boolean,
    defaultSymbol: String,
    mapOfCurrencySymbolsToFlag: MutableMap<String, String>,
    onSymbolSelected: (String) -> Unit
) {

    var textFieldSize by remember { mutableStateOf(Size.Zero)}
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedSymbol by rememberSaveable {mutableStateOf(defaultSymbol)}

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
                            modifier = Modifier.size(20.dp),
                            bitmap = Utils.getFlagImageBitMap(base64String),
                            contentDescription = "Flag",
                            tint = Color.Unspecified
                        )
                    }
            },
            textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold),
            singleLine = true,
            onValueChange = { newInput ->
                selectedSymbol = newInput
                onSymbolSelected(newInput)
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
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .height(250.dp)
        ) {
            mapOfCurrencySymbolsToFlag.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedSymbol = item.key
                        onSymbolSelected(item.key)
                        isExpanded = false
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        bitmap = Utils.getFlagImageBitMap(item.value),
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

@Composable
fun EditText(
    trailingText: String,
    modifier: Modifier,
    readOnly: Boolean,
    value: String,
    enabled: Boolean
) {

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
