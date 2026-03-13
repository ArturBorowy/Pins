package com.arturborowy.pins.ui.composable.settings

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
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.brand.designsystem.typography.titleSmallEmphasized
import com.arturborowy.pins.ui.composable.PreviewTheme

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

@Preview
@Composable
fun SettingSectionLabelPreview() =
    PreviewTheme {
        SettingSectionLabel(text = "Account")
    }

@Preview
@Composable
fun SettingItemClickablePreview() =
    PreviewTheme {
        SettingItem(label = "Privacy policy", onClick = {})
    }

@Preview
@Composable
fun SettingItemSecondaryValuePreview() =
    PreviewTheme {
        SettingItem(label = "App version", secondaryValue = "1.0.0")
    }

@Preview
@Composable
fun SettingItemNoDividerPreview() =
    PreviewTheme {
        SettingItem(label = "Sign out", onClick = {}, showDivider = false)
    }