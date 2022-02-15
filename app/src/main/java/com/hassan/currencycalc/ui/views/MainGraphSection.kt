package com.hassan.currencycalc.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hassan.currencycalc.R
import com.hassan.currencycalc.Utils
import com.hassan.currencycalc.viewmodels.MainViewModel

@Composable
fun MainGraphSection(mainViewModel: MainViewModel, base: String, symbols: String) {
    var date by rememberSaveable { mutableStateOf(Utils.todayDate()) }

    //due to the fact that the api service requires subscription, can't use this feature now
//    mainViewModel.getHistoricalRates(date, base, symbols)
//    val rates by mainViewModel.historicalRates.observeAsState(Rates())

    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {

        MainGraph(
            lines = Utils.getLines(),
            symbols = symbols,
            onDateSelected = { dateSelected -> date = dateSelected}
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(
            onClick = {  },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = stringResource(R.string.promotional_text),
                color = MaterialTheme.colors.primary,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}