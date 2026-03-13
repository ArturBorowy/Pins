package com.arturborowy.pins.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.SettingItem
import com.arturborowy.pins.ui.composable.SettingSectionLabel
import com.arturborowy.pins.ui.composable.SettingThemeSelector
import com.arturborowy.pins.ui.composable.SettingToggleItem
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(BrandTheme.spacing.screenPadding)
            .background(BrandTheme.colorScheme.background)
    ) {
        PageTitle(
            stringResource(R.string.settings_header),
            modifier = Modifier.padding(vertical = BrandTheme.spacing.screenPadding)
        )

        SettingSectionLabel(stringResource(R.string.settings_section_theme))

        SettingThemeSelector(
            selectedTheme = state.selectedTheme,
            onThemeSelected = viewModel::onThemeSelected
        )

        Column(
            modifier = Modifier
                .padding(top = BrandTheme.spacing.textSpacing)
                .shadow(BrandTheme.sizing.shadow, RoundedCornerShape(8.dp))
                .background(BrandTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp))
                .fillMaxWidth(),
        ) {
            SettingToggleItem(
                icon = Icons.Default.Palette,
                label = stringResource(R.string.settings_item_dynamic_colors),
                description = stringResource(R.string.settings_item_dynamic_colors_description),
                checked = state.isDynamicColorsEnabled,
                onCheckedChange = viewModel::onDynamicColorsToggled,
                showDivider = false
            )
        }

        SettingSectionLabel(stringResource(R.string.settings_section_about_app))

        Column(
            modifier = Modifier
                .shadow(BrandTheme.sizing.shadow, RoundedCornerShape(8.dp))
                .background(BrandTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp))
                .fillMaxWidth(),
        ) {
            SettingItem(
                stringResource(R.string.settings_item_licences),
                onClick = viewModel::onLicencesClick,
            )
            SettingItem(
                stringResource(R.string.settings_item_version),
                secondaryValue = state.versionNumber,
                showDivider = false
            )
        }

        SettingSectionLabel(stringResource(R.string.settings_section_other))

        Column(
            modifier = Modifier
                .shadow(BrandTheme.sizing.shadow, RoundedCornerShape(8.dp))
                .background(BrandTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp))
                .fillMaxWidth(),
        ) {
            SettingItem(
                stringResource(R.string.settings_item_rate_on_store),
                onClick = viewModel::onLicencesClick,
                showDivider = false
            )
        }
    }
}
