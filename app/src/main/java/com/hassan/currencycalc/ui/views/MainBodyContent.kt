package com.hassan.currencycalc.ui.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hassan.currencycalc.R
import com.hassan.currencycalc.ui.theme.RippleCustomTheme
import com.hassan.currencycalc.viewmodels.MainViewModel
import com.hassan.currencycalc.domain.entities.ConversionResult

@Composable
fun MainBodyContent(
    context: Context,
    modifier: Modifier,
    mapOfCurrencySymbolsToFlag: MutableMap<String, String>,
    mainViewModel: MainViewModel
) {
    //keep track of scroll state
    val scrollState = rememberScrollState()

    //initialize mutable states with default values as trailing texts for MainRateTextFields
    var baseCurrencySymbol by rememberSaveable { mutableStateOf("EUR") }
    var targetCurrencySymbol by rememberSaveable { mutableStateOf("PLN") }

    //initialize mutable state with default value as base amount for base currency
    //MainRateTextField
    var baseAmount by rememberSaveable { mutableStateOf("1")}

    //observe live data from conversion rate as state, every time state changes as a result of
    //new rates, recomposition ensues on every composable that uses this rates
    val conversionRate by mainViewModel.conversionRate.observeAsState(ConversionResult())

    Column(modifier = modifier.verticalScroll(scrollState)) {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            //compose app name
            AppName(modifier)

            //apply vertical spacing
            Spacer(modifier = Modifier.height(40.dp))

            //base text field
            MainRateTextField( 
                baseCurrencySymbol,
                modifier,
                readOnly = false,
                value = baseAmount.toString(),
                enabled = true,
                onBaseAmountChanged = { newBaseAmount -> baseAmount = newBaseAmount }
            )

            //apply vertical spacing between the text fields
            Spacer(modifier = Modifier.height(16.dp))

            //target text field
            MainRateTextField(
                targetCurrencySymbol,
                modifier,
                readOnly = false,
                value = if (conversionRate?.success != false) String
                    .format("%.6f", conversionRate?.result) else "",
                enabled = false,
                onBaseAmountChanged = { }
            )

            //vertical spacing between rate text field and currency pickers
            Spacer(modifier = Modifier.height(32.dp))

            //currency pickers row
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                //base currency picker
                MainCurrencyPicker(
                    modifier,
                    readOnly = false,
                    enabled = true,
                    defaultSymbol = "EUR",
                    mapOfCurrencySymbolsToFlag,
                    onSymbolSelected = { newText -> baseCurrencySymbol = newText }
                )

                //comparison arrow icon
                Icon(
                    imageVector = Icons.Filled.CompareArrows,
                    contentDescription = stringResource(R.string.compare_arrow_description),
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colors.onSecondary
                )

                //rate currency picker
                MainCurrencyPicker(
                    modifier,
                    readOnly = false,
                    enabled = true,
                    defaultSymbol = "PLN",
                    mapOfCurrencySymbolsToFlag,
                    onSymbolSelected = { newText -> targetCurrencySymbol = newText }
                )

            }

            //vertical spacing between currency pickers and convert button
            Spacer(modifier = Modifier.height(32.dp))

            //covert button
            Button(
                elevation = null,
                onClick = {
                    if(baseAmount.isNotEmpty()) {
                        mainViewModel.convert(
                            baseCurrencySymbol,
                            targetCurrencySymbol,
                            baseAmount.toDouble()
                        )

                        Toast.makeText(context, "Calculating..", Toast.LENGTH_SHORT).show()

                        //if conversion gives an error, toast the error message
                        if(conversionRate?.error != null) {
                            Toast.makeText(
                                context,
                                "${conversionRate?.error?.info}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(context, "Enter an amount", Toast.LENGTH_SHORT).show()
                    }
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
                //convert text
                Text(
                    text = stringResource(R.string.convert_text),
                    fontWeight = FontWeight.Bold, fontSize = 16.sp
                )
            }

            //vertical spacing between convert button and promo text
            Spacer(modifier = Modifier.height(16.dp))

            //set custom ripple that is applied locally to the children of this composable
            CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
                MainInfoText()
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        //graph section
        MainGraphSection(mainViewModel, baseCurrencySymbol, targetCurrencySymbol)
    }

}

