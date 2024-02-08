package com.arturborowy.pins.screen.edittrip

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arturborowy.pins.domain.PlaceDetails
import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.model.system.LocaleRepository
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.BaseViewModel
import com.ultimatelogger.android.output.ALog
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class EditTripViewModel @AssistedInject constructor(
    private val placesInteractor: PlacesInteractor,
    private val localeRepository: LocaleRepository,
    private val navigator: Navigator,
    @Assisted private val placeId: String
) : BaseViewModel() {

    val state = MutableStateFlow(State())

    private var arrivalDate: Date? = null
    private var departureDate: Date? = null

    private var selectedPlace: PlaceDetails? = null

    init {
        viewModelScope.launch {
            val tripSingleStop = placesInteractor.getSingleStopTrip(placeId)

            arrivalDate = Date(tripSingleStop.arrivalDate)

            tripSingleStop.departureDate?.let { departureDate = Date(it) }
            selectedPlace = PlaceDetails(
                tripSingleStop.locationName,
                tripSingleStop.latitude,
                tripSingleStop.longitude,
                tripSingleStop.country
            )

            state.emit(
                state.value.copy(
                    isSavingTripEnabled = true,
                    tripId = tripSingleStop.id,
                    placeText = tripSingleStop.locationName,
                    nameText = tripSingleStop.name,
                    placeLatitude = tripSingleStop.latitude,
                    placeLongitude = tripSingleStop.longitude,
                    departureDate = departureDate?.let { dateToString(it) },
                    arrivalDate = dateToString(arrivalDate!!),
                    placeCountryIcon = tripSingleStop.country.countryIcon
                )
            )
        }
    }

    override fun onResume(owner: LifecycleOwner) {
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
                validateSingleTripInput()
            }
        }
    }

    private fun validateSingleTripInput() {
        viewModelScope.launch {
            val allowSaving =
                state.value.placeText.isNotEmpty()
                        && state.value.nameText.isNotEmpty()
                        && state.value.arrivalDate?.isNotEmpty() == true
                        && state.value.departureDate?.isNotEmpty() == true
            state.emit(state.value.copy(isSavingTripEnabled = allowSaving))
        }
    }

    private suspend fun showAddressPredictions(placeText: String) {
        val addressTexts = placesInteractor.getAddressPredictions(placeText)
        state.emit(
            state.value.copy(predictions = addressTexts)
        )

        if (addressTexts.isNotEmpty()) {
            state.emit(
                state.value.copy(
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
        state.emit(
            state.value.copy(
                expandAddressPredictions = false,
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

    fun onBackEditingAddress() {
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    isAddressEditEnabled = true,
                    showConfirmAddressButton = false,
                    showExtraFields = false,
                    showBackSearchBarArrow = false,
                    placeText = "",
                    showKeyboard = true
                )
            )
        }
    }

    fun onConfirmAddress() {
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    showExtraFields = true,
                    isAddressEditEnabled = false,
                    showConfirmAddressButton = false,
                    showBackSearchBarArrow = true
                )
            )
        }
    }

    fun onArrivalDateChange(selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
        viewModelScope.launch {
            val date = dateValuesToDate(selectedYear, selectedMonth, selectedDayOfMonth)
            arrivalDate = date
            state.emit(state.value.copy(arrivalDate = dateToString(date), showKeyboard = false))
            validateSingleTripInput()
        }
    }


    private fun dateValuesToDate(
        selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int
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
            validateSingleTripInput()
        }
    }

    fun onSaveChangesClick() {
        viewModelScope.launch {
            placesInteractor.updateSingleStopTrip(
                state.value.tripId!!,
                state.value.nameText,
                arrivalDate!!,
                departureDate!!,
                selectedPlace!!
            )
            navigator.goBack()
        }
    }

    fun onTripCancelClick() {
        viewModelScope.launch {
            placesInteractor.removePlaceDetails(state.value.tripId!!)
            navigator.goBack()
        }
    }

    fun onTripNameChange(tripName: String) {
        viewModelScope.launch {
            state.emit(state.value.copy(nameText = tripName))
            validateSingleTripInput()
        }
    }

    data class State(
        val tripId: Long? = null,
        val predictions: List<AddressPredictionDto> = listOf(),
        val expandAddressPredictions: Boolean = false,
        val showExtraFields: Boolean = true,
        val placeId: String? = "",
        val placeText: String = "",
        val nameText: String = "",
        val placeTextChangedByUser: Boolean = false,
        val placeLatitude: Double? = null,
        val placeLongitude: Double? = null,
        @DrawableRes val placeCountryIcon: Int? = null,
        val showConfirmAddressButton: Boolean = false,
        val arrivalDate: String? = null,
        val departureDate: String? = null,
        val errorText: String? = null,
        val showKeyboard: Boolean = false,
        val isSavingTripEnabled: Boolean = false,
        val isAddressEditEnabled: Boolean = false,
        val showBackSearchBarArrow: Boolean = true
    )

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(placeId: String): EditTripViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            placeId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(placeId) as T
            }
        }
    }
}

@Composable
fun editTripViewModel(placeId: String): EditTripViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, MainActivity.ViewModelFactoryProvider::class.java
    ).editTripViewModelFactory()

    return viewModel(factory = EditTripViewModel.provideFactory(factory, placeId))
}