package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.spacing
import com.arturborowy.pins.ui.theme.titleSmallEmphasized

@Composable
fun SettingSectionLabel(text: String) {
    Text(
        text = text,
        style = PinsTheme.typography.labelMedium,
        color = PinsTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = PinsTheme.spacing.small)
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
                .padding(PinsTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = PinsTheme.typography.titleSmallEmphasized,
                color = PinsTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            when {
                secondaryValue != null -> Text(
                    text = secondaryValue,
                    style = PinsTheme.typography.bodyMedium,
                    color = PinsTheme.colorScheme.onSurfaceVariant
                )

                onClick != null -> Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = PinsTheme.colorScheme.onBackground
                )
            }
        }
        if (showDivider) {
            HorizontalDivider(color = PinsTheme.colorScheme.outlineVariant)
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