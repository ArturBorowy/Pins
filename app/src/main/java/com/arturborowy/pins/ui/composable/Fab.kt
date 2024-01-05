package com.arturborowy.pins.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R

@Composable
fun Fab(
    @DrawableRes iconResId: Int,
    @StringRes contentDescriptionResId: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        containerColor = Color.White,
        onClick = { onClick() },
        content = {
            Icon(
                modifier = Modifier.padding(12.dp),
                painter = painterResource(iconResId),
                tint = colorResource(R.color.primary),
                contentDescription = stringResource(contentDescriptionResId)
            )
        })
}