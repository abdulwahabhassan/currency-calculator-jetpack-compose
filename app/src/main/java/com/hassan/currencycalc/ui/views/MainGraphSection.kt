package com.hassan.currencycalc.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hassan.currencycalc.R
import com.hassan.currencycalc.utilities.Helpers
import com.hassan.currencycalc.viewmodels.MainViewModel
import com.hassan.currencycalc.domain.entities.RatesResult

@Composable
fun MainGraphSection(mainViewModel: MainViewModel, base: String, target: String) {

    //set initial dates
    var startDate by rememberSaveable { mutableStateOf(Helpers.calculatePastDate(30)) }
    val endDate by rememberSaveable { mutableStateOf(Helpers.todayDate()) }

    //get time series
    mainViewModel.getTimeSeriesRates(base, target, startDate, endDate)

    //collect time series rates as state, every time state changes as a result of
    //new rates, recomposition ensues on every composable that uses this rates
    val timeSeriesRates by mainViewModel.timeSeriesRates.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 300.dp)
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {

        //if list of data points is not null, plot graph
        Helpers.mapDataPoints(timeSeriesRates, target)?.let {
            MainGraph(
                line = it,
                symbols = target,
                onDateSelected = { dateSelected ->
                    startDate = dateSelected
                }
            )
        }

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