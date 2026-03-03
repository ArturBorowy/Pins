package com.arturborowy.pins.screen.triplist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.spacing

@Composable
fun BoxScope.EmptyListView(onAddTrip: () -> Unit) {
    PageTitle(
        text = stringResource(R.string.trip_list_header_empty),
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(
                PinsTheme.spacing.medium,
                PinsTheme.spacing.medium,
                PinsTheme.spacing.medium,
                0.dp
            )
    )
    Fab(
        R.drawable.ic_add_trip,
        R.string.main_bottom_nav_label_add,
        Modifier
            .padding(PinsTheme.spacing.medium)
            .align(Alignment.Center),
    ) { onAddTrip() }
    PageTitle(
        text = stringResource(R.string.trip_list_footer_empty),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(PinsTheme.spacing.medium)
    )
}

@Preview
@Composable
fun EmptyListViewPreview() = PreviewTheme {
    Box(Modifier.size(300.dp)) {
        EmptyListView {}
    }
}