package com.arturborowy.pins.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.titleSmallEmphasized
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(PinsTheme.colorScheme.background)
    ) {
        PageTitle(
            stringResource(R.string.settings_header),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        SettingSectionLabel(stringResource(R.string.settings_section_about_app))

        WideCard(padding = PaddingValues(0.dp)) {
            SettingItem(
                stringResource(R.string.settings_item_Licenses),
                onClick = viewModel::onLicensesClick,
            )
            SettingItem(
                stringResource(R.string.settings_item_version),
                secondaryValue = state.versionNumber,
                showDivider = false
            )
        }

        SettingSectionLabel(stringResource(R.string.settings_section_other))

        WideCard(padding = PaddingValues(0.dp)) {
            SettingItem(
                stringResource(R.string.settings_item_rate_on_store),
                onClick = viewModel::onLicensesClick,
                showDivider = false
            )
        }
    }
}

@Composable
fun SettingSectionLabel(text: String) {
    Text(
        text = text,
        style = PinsTheme.typography.labelMedium,
        color = PinsTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 8.dp)
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
                .padding(horizontal = 16.dp, vertical = 16.dp),
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
