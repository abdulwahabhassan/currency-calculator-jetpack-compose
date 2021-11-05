package com.hassan.currencycalc.ui.views

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import com.hassan.currencycalc.App
import com.hassan.currencycalc.ui.theme.CurrencyCalcTheme
import com.hassan.currencycalc.viewmodels.MainViewModel
import com.hassan.currencycalc.viewmodels.MainViewModelFactory

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
                    MainActivityScreen(viewModel = mainViewModel)
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
}


@Composable
fun MainActivityScreen(viewModel: ViewModel) {

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
                    TextButton(
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Text(
                            text = "Sign up",
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)

        )
    }

}

@Composable
fun BodyContent(modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {

        Column(modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp)) {
            AppName(modifier)
            Spacer(modifier = Modifier.height(40.dp))
            EditText("EUR", modifier)
            Spacer(modifier = Modifier.height(16.dp))
            EditText("PLN", modifier)
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                DropDownEditText(modifier)
                Icon(
                    imageVector = Icons.Filled.CompareArrows,
                    contentDescription = "Compare arrow",
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colors.onSecondary
                )
                DropDownEditText(modifier)

            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                elevation = null,
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.primary
                )
            ) {

                Text(text = "Convert", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
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

        GraphSection()
    }


}

@Composable
fun GraphSection() {
    Column(
        modifier = Modifier
            .height(500.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {

    }
}

@Composable
fun DropDownEditText(modifier: Modifier) {

    var textFieldSize by remember { mutableStateOf(Size.Zero)}
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val symbols = arrayListOf<String>("NGN", "MXN", "GBP", "USD")
    var selectedSymbol by rememberSaveable {mutableStateOf("")}

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
            value = "",
            singleLine = true,
            onValueChange = {},
            trailingIcon = {
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) "Show less" else "Show more",
                    Modifier
                        .clip(CircleShape)
                        .clickable { isExpanded = !isExpanded },
                    tint = MaterialTheme.colors.onSecondary
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onSecondary
            )
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = modifier
                    //This is where I used the coordinates from the OutlinedTextField to specify the
                    //width of the DropDrownMenu to be the same as the OutlinedTextField
                .width(with(LocalDensity.current){textFieldSize.width.toDp()}),
        ) {
            symbols.forEach { symbol ->
                DropdownMenuItem(
                    onClick = {
                        selectedSymbol = symbol
                        isExpanded = false
                    }
                ) {
//                    Icon(imageVector = , contentDescription = )
                    Text(text = symbol, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun EditText(text: String, modifier: Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        trailingIcon = {
            Text(
                text = text,
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold
            )
        },
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSecondary,
            backgroundColor = MaterialTheme.colors.secondary,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onSecondary
        )
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
