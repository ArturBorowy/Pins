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

    private val _state = MutableStateFlow(
        State(listOf(), null, false, false)
    )
    val state: StateFlow<State> = _state

    fun onViewResume(placeId: String?) {
        viewModelScope.launch {
            if (placeId != null) {
                val details = placesInteractor.getPlaceDetails(placeId)
                _state.emit(_state.value.copy(placeDetails = details, showRemoveBtn = true))
            }
        }
    }

    fun onAddressTextChange(text: String) {
        viewModelScope.launch {
            val addressTexts = placesInteractor.getAddressPredictions(text)
            _state.emit(_state.value.copy(predictions = addressTexts))

            if (addressTexts.isNotEmpty()) {
                _state.emit(_state.value.copy(expandAddressPredictions = true))
            }
        }
    }

    fun onAddressSelect(id: String) {
        viewModelScope.launch {
            val placeAddress = placesInteractor.getPlaceDetails(id)
            _state.emit(
                _state.value.copy(
                    placeDetails = placeAddress,
                    expandAddressPredictions = false
                )
            )
        }
    }

    fun onSaveAddress() {
        viewModelScope.launch {
            _state.value.placeDetails?.let {
                placesInteractor.savePlace(it)
            }
        }
    }

    fun onDescriptionTextChange(description: String) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    placeDetails = _state.value.placeDetails?.copy(description = description)
                )
            )
        }
    }

    fun onRemovePinClick() {
        viewModelScope.launch {
            _state.value.placeDetails?.let {
                placesInteractor.removePlaceDetails(it.id)
                _state.emit(_state.value.copy(placeDetails = null, showRemoveBtn = false))
            }
        }
    }

    data class State(
        val predictions: List<AddressPrediction>,
        val placeDetails: PlaceDetails?,
        val showRemoveBtn: Boolean,
        val expandAddressPredictions: Boolean
    )
}