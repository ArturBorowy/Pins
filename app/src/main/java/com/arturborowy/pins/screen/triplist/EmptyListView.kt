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
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PreviewTheme

@Composable
fun BoxScope.EmptyListView(onAddTrip: () -> Unit) {
    PageTitle(
        text = stringResource(R.string.trip_list_header_empty),
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(
                BrandTheme.spacing.screenPadding,
                BrandTheme.spacing.screenPadding,
                BrandTheme.spacing.screenPadding,
                0.dp
            )
    )
    Fab(
        R.drawable.ic_add_trip,
        R.string.main_bottom_nav_label_add,
        Modifier
            .padding(BrandTheme.spacing.screenPadding)
            .align(Alignment.Center),
    ) { onAddTrip() }
    PageTitle(
        text = stringResource(R.string.trip_list_footer_empty),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(BrandTheme.spacing.screenPadding)
    )
}

@Preview
@Composable
private fun EmptyListViewPreview() = PreviewTheme {
    Box(Modifier.size(300.dp)) {
        EmptyListView {}
    }
}