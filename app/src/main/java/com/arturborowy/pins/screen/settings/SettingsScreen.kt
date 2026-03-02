package com.arturborowy.pins.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.SettingItem
import com.arturborowy.pins.ui.composable.SettingSectionLabel
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsState()

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
