package com.arturborowy.pins.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.model.places.PlaceDetails
import com.arturborowy.pins.model.places.PlacesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val placesInteractor: PlacesInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state


    fun onViewResume() {
        viewModelScope.launch {
            val places = placesInteractor.getPlaces()
            _state.emit(State(places))
        }
    }

    data class State(
        val placeDetails: List<PlaceDetails> = listOf(),
    )
}