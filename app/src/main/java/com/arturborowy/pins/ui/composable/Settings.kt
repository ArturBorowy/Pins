package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.brand.designsystem.typography.titleSmallEmphasized
import com.arturborowy.pins.screen.settings.SettingsViewModel

@Composable
fun SettingSectionLabel(text: String) {
    Text(
        text = text,
        style = BrandTheme.typography.labelMedium,
        color = BrandTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(
            top = BrandTheme.spacing.cardPadding,
            bottom = BrandTheme.spacing.textSpacing
        )
    )
}

@Composable
fun SettingItem(
    label: String,
    secondaryValue: String? = null,
    onClick: (() -> Unit)? = null,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(BrandTheme.spacing.cardPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = BrandTheme.typography.titleSmallEmphasized,
                color = BrandTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            when {
                secondaryValue != null -> Text(
                    text = secondaryValue,
                    style = BrandTheme.typography.bodyMedium,
                    color = BrandTheme.colorScheme.onSurfaceVariant
                )

                onClick != null -> Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = BrandTheme.colorScheme.onBackground
                )
            }
        }
        if (showDivider) {
            HorizontalDivider(color = BrandTheme.colorScheme.outlineVariant)
        }
    }
}

@Composable
fun SettingThemeSelector(
    selectedTheme: SettingsViewModel.ThemeOption,
    onThemeSelected: (SettingsViewModel.ThemeOption) -> Unit
) {
    data class ThemeCard(
        val option: SettingsViewModel.ThemeOption,
        val label: String,
        val icon: ImageVector
    )

    val options = listOf(
        ThemeCard(SettingsViewModel.ThemeOption.LIGHT, "Light", Icons.Default.WbSunny),
        ThemeCard(SettingsViewModel.ThemeOption.DARK, "Dark", Icons.Default.DarkMode),
        ThemeCard(SettingsViewModel.ThemeOption.SYSTEM, "System", Icons.Default.DesktopWindows),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BrandTheme.spacing.textSpacing)
    ) {
        options.forEach { themeCard ->
            val isSelected = selectedTheme == themeCard.option
            val borderColor =
                if (isSelected) BrandTheme.colorScheme.primary else BrandTheme.colorScheme.outlineVariant
            val borderWidth = if (isSelected) 2.dp else 1.dp
            val contentColor =
                if (isSelected) BrandTheme.colorScheme.primary else BrandTheme.colorScheme.onSurfaceVariant

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onThemeSelected(themeCard.option) },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(borderWidth, borderColor),
                elevation = CardDefaults.cardElevation(defaultElevation = BrandTheme.sizing.shadow),
                colors = CardDefaults.cardColors(containerColor = BrandTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(BrandTheme.spacing.cardPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(BrandTheme.spacing.textSpacing)
                ) {
                    Icon(
                        imageVector = themeCard.icon,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = themeCard.label,
                        style = BrandTheme.typography.titleSmallEmphasized,
                        color = contentColor
                    )
                }
            }
        }
    }
}

@Composable
fun SettingToggleItem(
    icon: ImageVector,
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BrandTheme.spacing.cardPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BrandTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = BrandTheme.typography.titleSmallEmphasized,
                    color = BrandTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = BrandTheme.typography.bodySmall,
                    color = BrandTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
        if (showDivider) {
            HorizontalDivider(color = BrandTheme.colorScheme.outlineVariant)
        }
    }
}

@Preview
@Composable
fun SettingSectionLabelPreview() = PreviewTheme {
    SettingSectionLabel(text = "Account")
}

@Preview
@Composable
fun SettingItemClickablePreview() = PreviewTheme {
    SettingItem(label = "Privacy policy", onClick = {})
}

@Preview
@Composable
fun SettingItemSecondaryValuePreview() = PreviewTheme {
    SettingItem(label = "App version", secondaryValue = "1.0.0")
}

@Preview
@Composable
fun SettingItemNoDividerPreview() = PreviewTheme {
    SettingItem(label = "Sign out", onClick = {}, showDivider = false)
}