package com.arturborowy.pins.screen.map

import androidx.annotation.DrawableRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.model.places.AddressPrediction
import com.arturborowy.pins.model.places.PlaceDetails
import com.arturborowy.pins.model.places.PlacesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val placesInteractor: PlacesInteractor,
) : ViewModel(), DefaultLifecycleObserver {

    val state = MutableStateFlow(
        State(
            listOf(),
            false,
            false,
            false,
            null,
            "",
            false,
            ""
        )
    )

    private var selectedPlace: PlaceDetails? = null

    override fun onResume(owner: LifecycleOwner) {
        viewModelScope.launch {
            val places = placesInteractor.getPlaces()
            state.emit(state.value.copy(placeDetails = places))
        }
        viewModelScope.launch {
            state.collect {
                if (it.placeText.isNotEmpty() && it.placeTextChangedByUser) {
                    val addressTexts = placesInteractor.getAddressPredictions(it.placeText)
                    this@MapViewModel.state.emit(
                        this@MapViewModel.state.value.copy(predictions = addressTexts)
                    )

                    if (addressTexts.isNotEmpty()) {
                        this@MapViewModel.state.emit(
                            this@MapViewModel.state.value.copy(
                                expandAddressPredictions = true,
                                placeTextChangedByUser = true,
                            )
                        )
                    }
                }
            }
        }
    }

    fun onAddressSelect(id: String) {
        viewModelScope.launch {
            val placeAddress = placesInteractor.getPlaceDetails(id)
            this@MapViewModel.state.emit(
                this@MapViewModel.state.value.copy(
                    expandAddressPredictions = false,
                    areExtraFieldsVisible = true,
                    placeId = placeAddress.id,
                    placeText = placeAddress.label,
                    placeTextChangedByUser = false,
                    placeLatitude = placeAddress.latitude,
                    placeLongitude = placeAddress.longitude,
                    placeCountryIcon = placeAddress.country.countryIcon
                )
            )
            selectedPlace = placeAddress
        }
    }

    fun onSaveAddress() {
        viewModelScope.launch {
            placesInteractor.savePlace(selectedPlace!!)
            this@MapViewModel.state.emit(this@MapViewModel.state.value.copy(showRemoveBtn = true))
        }
    }

    fun onDescriptionTextChange(description: String) {
        viewModelScope.launch {
            this@MapViewModel.state.emit(this@MapViewModel.state.value.copy(placeDescription = description))
        }
    }

    fun onRemovePinClick() {
        viewModelScope.launch {
            placesInteractor.removePlaceDetails(this@MapViewModel.state.value.placeId!!)
            this@MapViewModel.state.emit(this@MapViewModel.state.value.copy(showRemoveBtn = false))
        }
    }

    fun onAddPinClick() {
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    showAddressTextField = true,
                    placeDetails = listOf()
                )
            )
        }
    }

    fun onBackEditingAddress() {
        viewModelScope.launch {
            val places = placesInteractor.getPlaces()
            state.emit(
                state.value.copy(
                    showAddressTextField = false,
                    placeDetails = places,
                    placeLongitude = null,
                    placeLatitude = null
                )
            )
        }
    }

    data class State(
        val predictions: List<AddressPrediction>,
        val showRemoveBtn: Boolean,
        val expandAddressPredictions: Boolean,
        val areExtraFieldsVisible: Boolean,
        val placeId: String?,
        val placeText: String,
        val placeTextChangedByUser: Boolean,
        val placeDescription: String,
        val placeLatitude: Double? = null,
        val placeLongitude: Double? = null,
        @DrawableRes val placeCountryIcon: Int? = null,
        val placeDetails: List<PlaceDetails> = listOf(),
        val showAddressTextField: Boolean = false
    )
}