package com.arturborowy.pins.screen.addpin

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
class AddPinViewModel @Inject constructor(
    private val placesInteractor: PlacesInteractor
) : ViewModel(), DefaultLifecycleObserver {

    var initPlaceId: String? = null

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
            if (initPlaceId != null) {
                val details = placesInteractor.getPlaceDetails(initPlaceId!!)
                this@AddPinViewModel.state.emit(
                    this@AddPinViewModel.state.value.copy(
                        showRemoveBtn = true,
                        areExtraFieldsVisible = true,
                        placeId = details.id,
                        placeText = details.label,
                        placeLatitude = details.latitude,
                        placeLongitude = details.longitude
                    )
                )
            }
        }
        viewModelScope.launch {
            state.collect {
                if (it.placeText.isNotEmpty() && it.placeTextChangedByUser) {
                    val addressTexts = placesInteractor.getAddressPredictions(it.placeText)
                    this@AddPinViewModel.state.emit(
                        this@AddPinViewModel.state.value.copy(predictions = addressTexts)
                    )

                    if (addressTexts.isNotEmpty()) {
                        this@AddPinViewModel.state.emit(
                            this@AddPinViewModel.state.value.copy(
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
            this@AddPinViewModel.state.emit(
                this@AddPinViewModel.state.value.copy(
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
            this@AddPinViewModel.state.emit(this@AddPinViewModel.state.value.copy(showRemoveBtn = true))
        }
    }

    fun onDescriptionTextChange(description: String) {
        viewModelScope.launch {
            this@AddPinViewModel.state.emit(this@AddPinViewModel.state.value.copy(placeDescription = description))
        }
    }

    fun onRemovePinClick() {
        viewModelScope.launch {
            placesInteractor.removePlaceDetails(this@AddPinViewModel.state.value.placeId!!)
            this@AddPinViewModel.state.emit(this@AddPinViewModel.state.value.copy(showRemoveBtn = false))
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
        @DrawableRes val placeCountryIcon: Int? = null
    )
}