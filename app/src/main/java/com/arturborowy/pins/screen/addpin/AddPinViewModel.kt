package com.arturborowy.pins.screen.addpin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.model.places.AddressPrediction
import com.arturborowy.pins.model.places.PlaceDetails
import com.arturborowy.pins.model.places.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPinViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    fun onAddressTextChange(text: String) {
        viewModelScope.launch {
            val addressTexts = placesRepository.getAddressPredictions(text)
            _state.emit(State(predictions = addressTexts))
        }
    }

    fun onAddressSelect(id: String) {
        viewModelScope.launch {
            val placeAddress = placesRepository.getPlaceDetails(id)
            _state.emit(State(placeDetails = placeAddress))
        }
    }

    data class State(
        val predictions : List<AddressPrediction> = listOf(),
        val placeDetails: PlaceDetails? = null
    )
}