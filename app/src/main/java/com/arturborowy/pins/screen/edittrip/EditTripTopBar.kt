package com.arturborowy.pins.screen.edittrip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.utils.ScreenshotTest

@Composable
fun EditTripTopBar(
    isPreviousStopAvailable: Boolean,
    onPreviousStopClick: () -> Unit,
    isNextStopAvailable: Boolean,
    onNextStopClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.alpha(if (isPreviousStopAvailable) 1f else 0f),
            onClick = onPreviousStopClick
        ) {
            Icon(
                modifier = Modifier
                    .size(BrandTheme.sizing.icon),
                painter = painterResource(R.drawable.ic_arrow_back),
                tint = BrandTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.edit_trip_cd_previous_stop)
            )
        }
        PageTitle(
            text = stringResource(R.string.edit_trip_header),
        )

        IconButton(
            modifier = Modifier.alpha(if (isNextStopAvailable) 1f else 0f),
            onClick = onNextStopClick
        ) {
            Icon(
                modifier = Modifier
                    .size(BrandTheme.sizing.icon),
                painter = painterResource(R.drawable.ic_arrow_forward),
                tint = BrandTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.edit_trip_cd_next_stop)
            )
        }
    }
}

@Preview
@ScreenshotTest
@Composable
private fun EditTripTopBarBothAvailablePreview() {
    PreviewTheme {
        EditTripTopBar(
            isPreviousStopAvailable = true,
            onPreviousStopClick = {},
            isNextStopAvailable = true,
            onNextStopClick = {}
        )
    }
}

@Preview
@ScreenshotTest
@Composable
private fun EditTripTopBarNoneAvailablePreview() {
    PreviewTheme {
        EditTripTopBar(
            isPreviousStopAvailable = false,
            onPreviousStopClick = {},
            isNextStopAvailable = false,
            onNextStopClick = {}
        )
    }
}