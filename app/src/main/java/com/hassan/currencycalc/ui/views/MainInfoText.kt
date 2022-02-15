package com.hassan.currencycalc.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hassan.currencycalc.R

@Composable
fun MainInfoText() {
    Column(Modifier.fillMaxWidth()) {
        TextButton(
            onClick = {  },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(6.dp)
        ) {
            //promo text
            Text(
                text = stringResource(R.string.info_text),
                color = MaterialTheme.colors.onPrimary,
                textDecoration = TextDecoration.Underline
            )

            //horizontal spacing between promo text and info icon
            Spacer(modifier = Modifier.width(8.dp))

            //info icon
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = stringResource(R.string.info_icon_description),
                modifier = Modifier.clip(CircleShape),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }

}