package com.arturborowy.pins.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
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
        containerColor = BrandTheme.colorScheme.primary,
        onClick = { onClick() },
        content = {
            Icon(
                modifier = Modifier.padding(BrandTheme.spacing.fabPadding),
                painter = painterResource(iconResId),
                tint = BrandTheme.colorScheme.onPrimary,
                contentDescription = stringResource(contentDescriptionResId)
            )
        })
}

@Preview
@Composable
private fun FabPreview() = PreviewTheme {
    Fab(
        iconResId = R.drawable.ic_add_trip,
        contentDescriptionResId = R.string.app_name,
        modifier = Modifier,
        onClick = {}
    )
}
