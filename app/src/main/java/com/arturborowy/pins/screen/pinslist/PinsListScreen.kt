package com.arturborowy.pins.screen.pinslist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PinsListScreen(viewModel: PinsListViewModel = hiltViewModel()) {
    viewModel.onViewResume()

    val state = viewModel.state.collectAsState()

    val addressDetails = state.value.placeDetails

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        items(addressDetails) {
            Button(
                onClick = { viewModel.onAddressClick(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = it.label)
            }
        }
    }
}