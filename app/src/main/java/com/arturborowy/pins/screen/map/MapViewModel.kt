package com.arturborowy.pins.screen.map

import androidx.annotation.DrawableRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.domain.PlaceDetails
import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.model.system.LocaleRepository
import com.arturborowy.pins.utils.BaseViewModel
import com.ultimatelogger.android.output.ALog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val placesInteractor: PlacesInteractor,
    private val localeRepository: LocaleRepository
) : BaseViewModel() {

    val state = MutableStateFlow(
        State(
            listOf(),
            false,
            false,
            false,
            null,
            "",
            "",
            false,
            "",
        )
    )

    private var arrivalDate: Date? = null
    private var departureDate: Date? = null

    private var selectedPlace: PlaceDetails? = null

    override fun onResume(owner: LifecycleOwner) {
        viewModelScope.launch {
            val places = placesInteractor.getPlaces()
                .map {
                    TripMarker(
                        it.name,
                        it.country.countryIcon,
                        it.latitude,
                        it.longitude
                    )
                }
            state.emit(state.value.copy(tripMarkers = places))
        }
        viewModelScope.launch {
            state.collect {
                if (it.placeText.isNotEmpty() && it.placeTextChangedByUser) {
                    try {
                        showAddressPredictions(it.placeText)
                    } catch (e: Exception) {
                        ALog.e(e)
                        state.emit(state.value.copy(errorText = e.message))
                    }
                }
            }
        }
    }

    private suspend fun showAddressPredictions(placeText: String) {
        val addressTexts = placesInteractor.getAddressPredictions(placeText)
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

    fun onAddressSelect(placeId: String) {
        viewModelScope.launch {
            try {
                loadPlacesDetails(placeId)
            } catch (e: Exception) {
                ALog.e(e)
                state.emit(state.value.copy(errorText = e.message))
            }
        }
    }

    private suspend fun loadPlacesDetails(placeId: String) {
        val placeAddress = placesInteractor.getPlaceDetails(placeId)
        this@MapViewModel.state.emit(
            this@MapViewModel.state.value.copy(
                expandAddressPredictions = false,
                placeId = placeId,
                showConfirmAddressButton = true,
                placeText = placeAddress.locationName,
                placeTextChangedByUser = false,
                placeLatitude = placeAddress.latitude,
                placeLongitude = placeAddress.longitude,
                placeCountryIcon = placeAddress.country.countryIcon
            )
        )
        selectedPlace = placeAddress
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
                    tripMarkers = listOf()
                )
            )
        }
    }

    fun onBackEditingAddress() {
        viewModelScope.launch {
            moveToPinListState()
        }
    }

    private suspend fun moveToPinListState() {
        val places = placesInteractor.getPlaces()
            .map {
                TripMarker(
                    it.name,
                    it.country.countryIcon,
                    it.latitude,
                    it.longitude
                )
            }
        state.emit(
            state.value.copy(
                showAddressTextField = false,
                tripMarkers = places,
                placeLongitude = null,
                placeLatitude = null,
                showConfirmAddressButton = false,
                areExtraFieldsVisible = false,
                placeText = ""
            )
        )
    }

    fun onConfirmAddress() {
        viewModelScope.launch {
            state.emit(state.value.copy(areExtraFieldsVisible = true))
        }
    }

    fun onArrivalDateChange(selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
        viewModelScope.launch {
            val date = dateValuesToDate(selectedYear, selectedMonth, selectedDayOfMonth)
            arrivalDate = date
            state.emit(state.value.copy(arrivalDate = dateToString(date)))
        }
    }


    private fun dateValuesToDate(
        selectedYear: Int,
        selectedMonth: Int,
        selectedDayOfMonth: Int
    ): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, selectedYear)
        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

        return Date(calendar.timeInMillis)
    }

    private fun dateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", localeRepository.locale)
        return dateFormat.format(date)
    }

    fun onDepartureDateChange(selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
        viewModelScope.launch {
            val date = dateValuesToDate(selectedYear, selectedMonth, selectedDayOfMonth)
            departureDate = date
            state.emit(state.value.copy(departureDate = dateToString(date)))
        }
    }

    fun onTripConfirmClick() {
        viewModelScope.launch {
            placesInteractor.saveSingleStopTrip(
                state.value.nameText,
                arrivalDate!!,
                departureDate!!,
                selectedPlace!!
            )

            moveToPinListState()

            // todo here move camera to last added pin
        }
    }

    fun onTripNameChange(tripName: String) {
        viewModelScope.launch {
            state.emit(state.value.copy(nameText = tripName))
        }
    }

    data class State(
        val predictions: List<AddressPredictionDto>,
        val showRemoveBtn: Boolean,
        val expandAddressPredictions: Boolean,
        val areExtraFieldsVisible: Boolean,
        val placeId: String?,
        val placeText: String,
        val nameText: String,
        val placeTextChangedByUser: Boolean,
        val placeDescription: String,
        val placeLatitude: Double? = null,
        val placeLongitude: Double? = null,
        @DrawableRes val placeCountryIcon: Int? = null,
        val tripMarkers: List<TripMarker> = listOf(),
        val showAddressTextField: Boolean = false,
        val showConfirmAddressButton: Boolean = false,
        val arrivalDate: String? = null,
        val departureDate: String? = null,
        val errorText: String? = null
    )
}