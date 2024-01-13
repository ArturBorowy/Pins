package com.arturborowy.pins.screen.pinslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.TripView
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun PinsListScreen(viewModel: PinsListViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary))
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            text = stringResource(R.string.pin_list_header)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            items(state.tripDetails) {
                TripView(it)
            }
        }
    }
}