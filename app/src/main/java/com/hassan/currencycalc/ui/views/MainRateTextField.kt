package com.hassan.currencycalc.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MainRateTextField(
    trailingText: String,
    modifier: Modifier,
    readOnly: Boolean,
    value: String,
    enabled: Boolean,
    onBaseAmountChanged: (newBaseAmount: String) -> Unit
) {
    //rememberable field value even when orientation and configuration changes
    var fieldValue by rememberSaveable { mutableStateOf(value) }

    //input text field
    TextField(
        value = value,
        readOnly = readOnly,
        enabled = enabled,
        onValueChange = { newValue -> onBaseAmountChanged(newValue)},
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
        //keyboard should only show numbers
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}