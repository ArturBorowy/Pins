package com.arturborowy.pins.addpin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPinScreen(viewModel: AddPinViewModel = hiltViewModel()) {
    var text by remember { mutableStateOf("") }

    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.teal_700))
            .wrapContentSize(Alignment.TopStart)
    ) {
        Box {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    viewModel.onAddressTextChange(it)
                    text = it
                },
                label = { Text("label") },
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = true,
                properties = PopupProperties(
                    clippingEnabled = false,
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = {}
            ) {
                state.value.forEach { text ->
                    DropdownMenuItem(
                        onClick = {

                        },
                        text = { Text(text) }
                    )
                }
            }
        }
    }
}