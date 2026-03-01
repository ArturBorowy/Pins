package com.arturborowy.pins.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
    ) {
        PageTitle(
            stringResource(R.string.settings_header),
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            WideCard(padding = PaddingValues(0.dp, 8.dp, 0.dp, 0.dp)) {
                SettingItem(stringResource(R.string.settings_item_rate_on_store))
                SettingItem(
                    stringResource(R.string.settings_item_Licenses),
                    onClick = viewModel::onLicensesClick
                )
                SettingItem(
                    stringResource(R.string.settings_item_version),
                    state.versionNumber
                )
            }
        }
    }
}

@Composable
fun SettingItem(label: String, secondaryValue: String? = null, onClick: (() -> Unit)? = null) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick ?: {},
        colors = ButtonDefaults.textButtonColors(
            disabledContentColor = PinsTheme.colorScheme.onSurface,
            contentColor = PinsTheme.colorScheme.onSurface
        ),
        enabled = onClick != null,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = label,
                    style = PinsTheme.typography.titleSmall
                )
                secondaryValue?.let { secondaryValue ->
                    Text(
                        text = secondaryValue,
                        style = PinsTheme.typography.titleSmall
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(0.dp, 2.dp, 0.dp, 0.dp)
                    .background(PinsTheme.colorScheme.primary)
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}