package com.arturborowy.pins.screen.addpin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.model.places.AddressPrediction
import com.arturborowy.pins.model.places.PlaceDetails
import com.arturborowy.pins.model.places.PlacesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPinViewModel @Inject constructor(
    private val placesInteractor: PlacesInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    private var selectedPlace: PlaceDetails? = null

    fun onViewResume(placeId: String?) {
        viewModelScope.launch {
            if (placeId != null) {
                val details = placesInteractor.getPlaceDetails(placeId)
                _state.emit(State(placeDetails = details))
            }
        }
    }

    fun onAddressTextChange(text: String) {
        viewModelScope.launch {
            val addressTexts = placesInteractor.getAddressPredictions(text)
            _state.emit(State(predictions = addressTexts))
        }
    }

    fun onAddressSelect(id: String) {
        viewModelScope.launch {
            val placeAddress = placesInteractor.getPlaceDetails(id)
            selectedPlace = placeAddress
            _state.emit(State(placeDetails = placeAddress))
        }
    }

    fun onSaveAddress() {
        viewModelScope.launch {
            selectedPlace?.let { placesInteractor.savePlace(it) }
        }
    }

    data class State(
        val predictions: List<AddressPrediction> = listOf(),
        val placeDetails: PlaceDetails? = null
    )
}