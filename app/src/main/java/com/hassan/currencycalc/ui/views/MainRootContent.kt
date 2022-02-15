package com.hassan.currencycalc.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hassan.currencycalc.R
import com.hassan.currencycalc.ui.theme.RippleCustomTheme
import com.hassan.currencycalc.ui.theme.CurrencyCalcTheme
import com.hassan.currencycalc.viewmodels.MainViewModel

@Composable
fun MainRootContent(
    mainViewModel: MainViewModel,
    mapOfCurrencySymbolsToFlag: MutableMap<String, String>
) {
    //use default theme
    CurrencyCalcTheme {
        Scaffold(
            topBar = { //top app bar
                TopAppBar(
                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                    elevation = 0.dp,
                    title = {},
                    navigationIcon = { //menu icon
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(
                                    R.string.nav_menu_icon_description
                                ),
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                    },
                    actions = { //set custom ripple that is applied locally to the children of
                        //this composable
                        CompositionLocalProvider(
                            LocalRippleTheme provides RippleCustomTheme
                        ) { //sign-up button
                            TextButton(onClick = {}, shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = stringResource(R.string.sign_up_button_text),
                                    color = MaterialTheme.colors.primaryVariant,
                                    style = MaterialTheme.typography.subtitle1
                                )
                            }
                        }
                    }
                )
            }
        ) { innerPadding -> //body content of main activity
            MainBodyContent(
                Modifier.padding(innerPadding),
                mapOfCurrencySymbolsToFlag,
                mainViewModel
            )
        }
    }
}