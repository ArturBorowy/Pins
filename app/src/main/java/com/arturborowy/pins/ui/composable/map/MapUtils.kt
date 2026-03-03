package com.arturborowy.pins.ui.composable.map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.arturborowy.pins.R
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

@Composable
internal fun themedMapProperties(): MapProperties {
    val context = LocalContext.current

    return MapProperties(
        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
            context,
            if (isSystemInDarkTheme()) R.raw.map_style_options_dark
            else R.raw.map_style_options_light
        )
    )
}
