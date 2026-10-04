package com.arturborowy.pins.ui.composable

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.ui.composable.map.LocalUseMockMap

@Composable
fun PreviewTheme(
    composable: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalUseMockMap provides true) {
        BrandTheme { composable() }
    }
}

@Preview("Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewLightAndDark
