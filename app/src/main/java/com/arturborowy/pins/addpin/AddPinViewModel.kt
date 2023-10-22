package com.arturborowy.pins.addpin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPinViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(listOf<AddressPrediction>())
    val state: StateFlow<List<AddressPrediction>> = _state

    fun onAddressTextChange(text: String) {
        viewModelScope.launch {
            val addressTexts = placesRepository.getAddressPredictions(text)
            _state.emit(addressTexts)
        }
    }

    fun onAddressSelect(id: String) {
        viewModelScope.launch {
            val placeAddress = placesRepository.getPlaceDetails(id)
        }
    }
}