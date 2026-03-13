package com.arturborowy.pins.ui.composable.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Palette
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
import com.arturborowy.pins.ui.composable.PreviewTheme

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
fun SettingToggleItemChecked() = PreviewTheme {
    SettingToggleItem(
        Icons.Default.Palette,
        "Style",
        "Change app's theme",
        true,
        {},
        false
    )
}

@Preview
@Composable
fun SettingToggleItemUnchecked() = PreviewTheme {
    SettingToggleItem(
        Icons.Default.Accessibility,
        "Accessibility",
        "Show accessibility options",
        false,
        {},
        false
    )
}