package com.arturborowy.pins.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.arturborowy.pins.ui.composable.PreviewLightAndDark
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.composable.settings.SettingItem
import com.arturborowy.pins.ui.composable.settings.SettingSectionLabel
import com.arturborowy.pins.utils.ScreenshotTest
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsView(
        versionNumber = state.versionNumber,
        onAppearanceClick = viewModel::onAppearanceClick,
        onLicencesClick = viewModel::onLicencesClick
    )
}

@Composable
private fun SettingsView(
    versionNumber: String,
    onAppearanceClick: () -> Unit,
    onLicencesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background)
            .padding(BrandTheme.spacing.screenPadding)
    ) {
        PageTitle(
            stringResource(R.string.settings_header),
            modifier = Modifier.padding(vertical = BrandTheme.spacing.screenPadding)
        )

        SettingSectionLabel(stringResource(R.string.settings_section_theme))

        Column(
            modifier = Modifier
                .shadow(BrandTheme.sizing.shadow, RoundedCornerShape(8.dp))
                .background(BrandTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp))
                .fillMaxWidth(),
        ) {
            SettingItem(
                stringResource(R.string.settings_item_appearance),
                onClick = onAppearanceClick,
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
                onClick = onLicencesClick,
            )
            SettingItem(
                stringResource(R.string.settings_item_version),
                secondaryValue = versionNumber,
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
                onClick = onLicencesClick,
                showDivider = false
            )
        }
    }
}

@PreviewLightAndDark
@ScreenshotTest
@Composable
private fun SettingsViewPreview() = PreviewTheme {
    SettingsView(
        versionNumber = "1.0.0",
        onAppearanceClick = {},
        onLicencesClick = {}
    )
}
