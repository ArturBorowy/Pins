package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arturborowy.brand.designsystem.BrandTheme

@Composable
fun PreviewTheme(
    composable: @Composable () -> Unit,
) {
    Column {
        BrandTheme(false, useDynamicColors = false, content = {
            Box(
                Modifier
                    .background(BrandTheme.colorScheme.background)
                    .padding(BrandTheme.spacing.previewPadding)
            ) {
                composable()
            }
        })
        Spacer(Modifier.height(BrandTheme.spacing.previewPadding))
        BrandTheme(true, useDynamicColors = false, content = {
            Box(
                Modifier
                    .background(BrandTheme.colorScheme.background)
                    .padding(BrandTheme.spacing.previewPadding)
            ) {
                composable()
            }
        })
    }
}
