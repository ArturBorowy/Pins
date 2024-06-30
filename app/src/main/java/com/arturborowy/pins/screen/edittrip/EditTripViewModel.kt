package com.arturborowy.pins.screen.edittrip

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.PlaceDetails
import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.domain.StopDetails
import com.arturborowy.pins.domain.Trip
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.model.system.LocaleRepository
import com.arturborowy.pins.model.system.NetworkStateRepository
import com.arturborowy.pins.model.system.ResourcesRepository
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
    private val networkStateRepository: NetworkStateRepository,
    private val resourcesRepository: ResourcesRepository,
    private val navigator: Navigator,
    @Assisted private val tripId: String
) : BaseViewModel() {

    val state = MutableStateFlow(State())

    init {
        viewModelScope.launch {
            val trip = placesInteractor.getSingleStopTrip(tripId)

            state.emit(
                state.value.copy(
                    isSavingTripEnabled = true,
                    tripId = trip.id,
                    tripName = trip.name,
                    stops = trip.stops.map {
                        EditTripStopItem(
                            it.placeDetails.locationName,
                            dateToString(it.arrivalDate),
                            it.departureDate?.let { dateToString(it) },
                            it.placeDetails.latitude,
                            it.placeDetails.longitude,
                            it.placeDetails.country
                        )
                    },
                )
            )

            onPageChanged(0)
        }

        viewModelScope.launch {
            networkStateRepository.hasInternet.collect {
                state.emit(
                    state.value.copy(
                        placeErrorText = if (it) {
                            null
                        } else {
                            resourcesRepository.getString(R.string.add_trip_error_internet_unavailable)
                        }
                    )
                )
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        viewModelScope.launch {
            state.collect {
                if (it.tripName.isEmpty().not() && it.placeTextChangedByUser) {
                    try {
                        showAddressPredictions(it.stops[it.currentStopId].locationName)
                    } catch (e: Exception) {
                        ALog.e(e)
                        state.emit(state.value.copy(errorText = e.message))
                    }
                }
                validateTripInput()
            }
        }
    }

    private fun validateTripInput() {
        viewModelScope.launch {
            val allowSaving = validateStopsInput() && state.value.tripName.isNotEmpty()
            state.emit(state.value.copy(isSavingTripEnabled = allowSaving))
        }
    }

    private fun validateStopsInput() =
        state.value.stops.all { validateStopInput(it) }

    private fun validateStopInput(stop: EditTripStopItem) =
        stop.locationName.isNotEmpty() && stop.arrivalDateStr.isNotEmpty()

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
                placeTextChangedByUser = false,
                stops = state.value.stops
                    .toMutableList()
                    .apply {
                        set(
                            state.value.currentStopId,
                            get(state.value.currentStopId).copy(
                                locationName = placeAddress.locationName,
                                latitude = placeAddress.latitude,
                                longitude = placeAddress.longitude,
                                country = placeAddress.country
                            )
                        )
                    }
            )
        )
    }

    fun onBackEditingAddress() {
        viewModelScope.launch {
            if (state.value.isAddressEditEnabled) {
                onConfirmAddress()
            } else {
                state.emit(
                    state.value.copy(
                        isPreviousStopAvailable = false,
                        isNextStopAvailable = false,
                        isAddressEditEnabled = true,
                        showConfirmAddressButton = false,
                        showExtraFields = false,
                        showKeyboard = true
                    )
                )
            }
        }
    }

    fun onConfirmAddress() {
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    showExtraFields = true,
                    isAddressEditEnabled = false,
                    showConfirmAddressButton = false,
                    isPreviousStopAvailable = state.value.currentStopId > 0,
                    isNextStopAvailable = (state.value.currentStopId < state.value.stops.size - 1)
                )
            )
        }
    }

    fun onArrivalDateChange(selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
        viewModelScope.launch {
            val date = dateValuesToDate(selectedYear, selectedMonth, selectedDayOfMonth)

            state.tryEmit(state.value.copy(
                stops = state.value
                    .stops
                    .toMutableList()
                    .apply {
                        set(
                            state.value.currentStopId,
                            get(state.value.currentStopId)
                                .copy(arrivalDateStr = dateToString(date))
                        )
                    }
            ))

            validateTripInput()
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

    private fun stringToDate(string: String): Date {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", localeRepository.locale)
        return dateFormat.parse(string)!!
    }

    fun onDepartureDateChange(selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
        viewModelScope.launch {
            val date = dateValuesToDate(selectedYear, selectedMonth, selectedDayOfMonth)

            state.tryEmit(state.value.copy(
                stops = state.value
                    .stops
                    .toMutableList()
                    .apply {
                        set(
                            state.value.currentStopId,
                            get(state.value.currentStopId)
                                .copy(departureDateStr = dateToString(date))
                        )
                    }
            ))

            validateTripInput()
        }
    }

    fun onSaveChangesClick() {
        viewModelScope.launch {
            placesInteractor.updateTrip(
                Trip(
                    state.value.tripId!!,
                    state.value.tripName,
                    state.value.stops.map { stop ->
                        StopDetails(
                            stringToDate(stop.arrivalDateStr),
                            stop.departureDateStr?.let { stringToDate(it) },
                            PlaceDetails(
                                stop.locationName,
                                stop.latitude,
                                stop.longitude,
                                stop.country
                            ),
                        )
                    }
                )
            )
            navigator.goBack()
        }
    }

    fun onTripRemoveClick() {
        viewModelScope.launch {
            placesInteractor.removePlaceDetails(state.value.tripId!!)
            navigator.goBack()
        }
    }

    fun onTripNameChange(tripName: String) {
        viewModelScope.launch {
            state.emit(state.value.copy(tripName = tripName))
            validateTripInput()
        }
    }

    fun onAddNextStopClick() {
        TODO()
    }

    fun onPreviousStopClick() {
        if (state.value.isPreviousStopAvailable) {
            state.tryEmit(state.value.copy(currentStopId = state.value.currentStopId - 1))
        }
    }

    fun onNextStopClick() {
        if (state.value.isNextStopAvailable) {
            state.tryEmit(state.value.copy(currentStopId = state.value.currentStopId + 1))
        }
    }

    fun onPageChanged(page: Int) {
        state.tryEmit(
            state.value.copy(
                currentStopId = page,
                isPreviousStopAvailable = page > 0,
                isNextStopAvailable = (page < state.value.stops.size - 1)
            )
        )
    }

    fun onSearchTextChange(placeText: String) {
        state.tryEmit(
            state.value.copy(
                stops = state.value
                    .stops
                    .toMutableList()
                    .apply {
                        set(
                            state.value.currentStopId,
                            get(state.value.currentStopId)
                                .copy(locationName = placeText)
                        )
                    },
                placeTextChangedByUser = true
            )
        )
    }

    data class State(
        val tripId: Long? = null,
        val tripName: String = "",
        val stops: List<EditTripStopItem> = listOf(),
        val predictions: List<AddressPredictionDto> = listOf(),
        val expandAddressPredictions: Boolean = false,
        val showExtraFields: Boolean = true,
        val placeErrorText: String? = null,
        val placeTextChangedByUser: Boolean = false,
        val showConfirmAddressButton: Boolean = false,
        val errorText: String? = null,
        val showKeyboard: Boolean = false,
        val isSavingTripEnabled: Boolean = false,
        val isAddressEditEnabled: Boolean = false,
        val currentStopId: Int = 0,
        val isPreviousStopAvailable: Boolean = false,
        val isNextStopAvailable: Boolean = false,
    )

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(tripId: String): EditTripViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            tripId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(tripId) as T
            }
        }
    }
}

@Composable
fun editTripViewModel(tripId: String): EditTripViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, MainActivity.ViewModelFactoryProvider::class.java
    ).editTripViewModelFactory()

    return viewModel(factory = EditTripViewModel.provideFactory(factory, tripId))
}