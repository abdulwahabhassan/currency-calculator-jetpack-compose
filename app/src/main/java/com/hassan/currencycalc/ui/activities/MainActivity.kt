package com.hassan.currencycalc.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hassan.currencycalc.utilities.Helpers
import com.hassan.currencycalc.ui.views.MainRootContent
import com.hassan.currencycalc.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //initialize view model
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //compose main root content
            MainRootContent(
                this,
                mainViewModel = mainViewModel,
                mapOfCurrencySymbolsToFlag = Helpers.loadMapOfCurrencySymbolToFlag(assets)
            )

        }
    }
}








