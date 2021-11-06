package com.hassan.currencycalc.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object RippleCustomTheme: RippleTheme {
    //This is used to provide a custom ripple theme
    @Composable
    override fun defaultColor(): Color {
        return RippleTheme.defaultRippleColor(MaterialTheme.colors.onPrimary, lightTheme = true)
    }

    @Composable
    override fun rippleAlpha(): RippleAlpha {
       return  RippleTheme.defaultRippleAlpha(MaterialTheme.colors.onPrimary, lightTheme = true)
    }

}