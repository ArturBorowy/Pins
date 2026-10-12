package com.arturborowy.pins.screen.settings.appearance

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
import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PreviewLightAndDark
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.composable.settings.SettingSectionLabel
import com.arturborowy.pins.ui.composable.settings.SettingThemeSelector
import com.arturborowy.pins.ui.composable.settings.SettingToggleItem
import com.arturborowy.pins.utils.ScreenshotTest
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun AppearanceSettingsScreen(viewModel: AppearanceSettingsViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

    AppearanceSettingsView(
        selectedTheme = state.selectedTheme,
        isDynamicColorsEnabled = state.isDynamicColorsEnabled,
        onThemeSelected = viewModel::onThemeSelected,
        onDynamicColorsToggled = viewModel::onDynamicColorsToggled,
    )
}

@Composable
private fun AppearanceSettingsView(
    selectedTheme: AppVisualTheme,
    isDynamicColorsEnabled: Boolean,
    onThemeSelected: (AppVisualTheme) -> Unit,
    onDynamicColorsToggled: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background)
            .padding(BrandTheme.spacing.screenPadding)
    ) {
        PageTitle(
            text = stringResource(R.string.settings_item_appearance),
            modifier = Modifier.padding(vertical = BrandTheme.spacing.screenPadding)
        )

        SettingSectionLabel(stringResource(R.string.settings_section_theme))

        SettingThemeSelector(
            selectedTheme = selectedTheme,
            onThemeSelected = onThemeSelected
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
                checked = isDynamicColorsEnabled,
                onCheckedChange = onDynamicColorsToggled,
                showDivider = false
            )
        }
    }
}

@PreviewLightAndDark
@ScreenshotTest
@Composable
private fun AppearanceSettingsViewPreview() = PreviewTheme {
    AppearanceSettingsView(
        selectedTheme = AppVisualTheme.FOLLOW_SYSTEM,
        isDynamicColorsEnabled = true,
        onThemeSelected = {},
        onDynamicColorsToggled = {}
    )
}
