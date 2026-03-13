package com.arturborowy.pins.ui.composable.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.brand.designsystem.typography.titleSmallEmphasized
import com.arturborowy.pins.R
import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.ui.composable.PreviewTheme

@Composable
fun SettingThemeSelector(
    selectedTheme: AppVisualTheme,
    onThemeSelected: (AppVisualTheme) -> Unit
) {
    data class ThemeCard(
        val option: AppVisualTheme,
        val label: String,
        val icon: ImageVector
    )

    val options = listOf(
        ThemeCard(
            AppVisualTheme.LIGHT,
            stringResource(R.string.settings_theme_light),
            Icons.Default.WbSunny
        ),
        ThemeCard(
            AppVisualTheme.DARK,
            stringResource(R.string.settings_theme_dark),
            Icons.Default.DarkMode
        ),
        ThemeCard(
            AppVisualTheme.FOLLOW_SYSTEM,
            stringResource(R.string.settings_theme_system),
            Icons.Default.DesktopWindows
        ),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BrandTheme.spacing.textSpacing)
    ) {
        options.forEach { themeCard ->
            ThemeOptionCard(
                modifier = Modifier.weight(1f),
                label = themeCard.label,
                icon = themeCard.icon,
                isSelected = selectedTheme == themeCard.option,
                onClick = { onThemeSelected(themeCard.option) }
            )
        }
    }
}

@Composable
private fun ThemeOptionCard(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor =
        if (isSelected) BrandTheme.colorScheme.primary else BrandTheme.colorScheme.outlineVariant
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val contentColor =
        if (isSelected) BrandTheme.colorScheme.primary else BrandTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(borderWidth, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = BrandTheme.sizing.shadow),
        colors = CardDefaults.cardColors(containerColor = BrandTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BrandTheme.spacing.cardPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BrandTheme.spacing.textSpacing)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = label,
                style = BrandTheme.typography.titleSmallEmphasized,
                color = contentColor
            )
        }
    }
}

@Composable
@Preview
private fun ThemeOptionCardPreview() = PreviewTheme {
    ThemeOptionCard(
        label = "Light",
        icon = Icons.Default.WbSunny,
        isSelected = true,
        onClick = {}
    )
}

@Composable
@Preview
private fun SettingThemeSelectorPreview() = PreviewTheme {
    SettingThemeSelector(AppVisualTheme.FOLLOW_SYSTEM) { }
}