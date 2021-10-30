package com.hassan.currencycalc.ui.views

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hassan.currencycalc.App
import com.hassan.currencycalc.ui.theme.CurrencyCalcTheme
import com.hassan.currencycalc.viewmodels.MainViewModel
import com.hassan.currencycalc.viewmodels.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (this.application as App).getRatesUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("remote rates", "create" )

        mainViewModel.getRates()
        mainViewModel.remoteRates.observe(this, {
            Log.d("remote rates", "$it")}
        )

        setContent {
            CurrencyCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyCalcTheme {
        Greeting("Android")
    }
}