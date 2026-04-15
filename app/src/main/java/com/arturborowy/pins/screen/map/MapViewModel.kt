package com.arturborowy.pins.screen.map

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.data.system.LocaleRepository
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.data.system.ResourcesRepository
import com.arturborowy.pins.domain.AddressPrediction
import com.arturborowy.pins.domain.PlaceDetailsWithCountry
import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.domain.StopDetails
import com.arturborowy.pins.domain.Trip
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.utils.BaseViewModel
import com.ultimatelogger.android.output.ALog
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

enum class AddingTripStep {
    ADD_TRIP_BUTTON, TRIP_TYPE_BAR, FORM
}

class MapViewModel @AssistedInject constructor(
    private val placesInteractor: PlacesInteractor,
    private val localeRepository: LocaleRepository,
    private val networkStateRepository: NetworkStateRepository,
    private val resourcesRepository: ResourcesRepository,
    @Assisted private val showTripTypeBar: Boolean
) : BaseViewModel() {

    val state = MutableStateFlow(
        State(addingTripStep = if (showTripTypeBar) AddingTripStep.TRIP_TYPE_BAR else AddingTripStep.ADD_TRIP_BUTTON)
    )

    val errorEvents = MutableSharedFlow<String>(extraBufferCapacity = 1)

    private var arrivalDate: Date? = null
    private var departureDate: Date? = null

    private var selectedPlace: PlaceDetailsWithCountry? = null

    private val stops = mutableListOf<StopDetails>()

    private var job: Job? = null

    override fun onResume(owner: LifecycleOwner) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            launch {
                try {
                    val tripMarkers = placesInteractor.getPlaces().toTripMarkers()
                    state.emit(state.value.copy(tripMarkers = tripMarkers))
                } catch (e: Exception) {
                    ALog.e(e)
                    errorEvents.tryEmit(e.message ?: "")
                }
                state.collect {
                    if (it.placeText.isNotEmpty() && it.placeTextChangedByUser) {
                        try {
                            showAddressPredictions(it.placeText)
                        } catch (e: Exception) {
                            ALog.e(e)
                            errorEvents.tryEmit(e.message ?: "")
                        }
                    }
                }
            }
            launch {
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
    }

    override fun onPause(owner: LifecycleOwner) {
        job?.cancel()
    }

    private suspend fun showAddressPredictions(placeText: String) {
        val addressTexts = placesInteractor.getAddressPredictions(placeText)
        state.emit(
            state.value.copy(predictions = addressTexts)
        )

        if (addressTexts.isNotEmpty()) {
            state.emit(
                state.value.copy(placeTextChangedByUser = true)
            )
        }
    }

    fun onAddressSelect(placeId: AddressPrediction.Id) {
        viewModelScope.launch {
            try {
                loadPlacesDetails(placeId)
            } catch (e: Exception) {
                ALog.e(e)
                errorEvents.tryEmit(e.message ?: "")
            }
        }
    }

    private suspend fun loadPlacesDetails(placeId: AddressPrediction.Id) {
        val placeAddress = placesInteractor.getPlaceDetails(placeId)
        state.emit(
            state.value.copy(
                placeId = placeId,
                showConfirmAddressButton = true,
                placeText = placeAddress.locationName,
                placeTextChangedByUser = false,
                marker = MapMarkerItem(
                    placeAddress.locationName,
                    placeAddress.country.countryIcon,
                    placeAddress.latitude,
                    placeAddress.longitude,
                ),
            )
        )
        selectedPlace = placeAddress
    }

    fun onAddTripClick() {
        viewModelScope.launch {
            state.emit(state.value.copy(addingTripStep = AddingTripStep.TRIP_TYPE_BAR))
        }
    }

    fun onAddSingleStopTripClick() {
        showAddressTextField(false)
    }

    private fun showAddressTextField(multiStop: Boolean) {
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    addingTripStep = AddingTripStep.FORM,
                    tripMarkers = listOf(),
                    isAddressEditEnabled = true,
                    multiStop = multiStop
                )
            )
        }
    }

    fun onAddMultiStopTripClick() {
        showAddressTextField(true)
    }

    fun onBackEditingAddress() {
        viewModelScope.launch {
            if (state.value.showExtraFields) {
                state.emit(
                    state.value.copy(
                        isAddressEditEnabled = true,
                        showConfirmAddressButton = false,
                        showExtraFields = false,
                        placeText = ""
                    )
                )
            } else {
                onTripCancelClick()
            }
        }
    }

    private suspend fun moveToTripListState() {
        val tripMarkers = placesInteractor.getPlaces().toTripMarkers()
        state.emit(
            state.value.copy(
                addingTripStep = AddingTripStep.ADD_TRIP_BUTTON,
                tripMarkers = tripMarkers,
                marker = null,
                showConfirmAddressButton = false,
                showExtraFields = false,
                placeText = "",
                departureDate = null,
                arrivalDate = null,
                nameText = "",
            )
        )
    }

    private fun List<Trip>.toTripMarkers() =
        map { trip ->
            trip.stops.map { stop ->
                MapMarkerItem(
                    stop.placeDetailsWithCountry.locationName,
                    stop.placeDetailsWithCountry.country.countryIcon,
                    stop.placeDetailsWithCountry.latitude,
                    stop.placeDetailsWithCountry.longitude
                )
            }
        }

    fun onConfirmAddress() {
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    showExtraFields = true,
                    isAddressEditEnabled = false,
                    showConfirmAddressButton = false
                )
            )
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
        }
    }

    fun onTripConfirmClick() {
        viewModelScope.launch {
            if (state.value.multiStop) {
                stops.add(
                    StopDetails(
                        arrivalDate!!, departureDate, selectedPlace!!
                    )
                )

                placesInteractor.saveMultiStopTrip(state.value.nameText, stops)
            } else {
                placesInteractor.saveSingleStopTrip(
                    state.value.nameText,
                    StopDetails(arrivalDate!!, departureDate!!, selectedPlace!!)
                )
            }

            moveToTripListState()

            // todo here move camera to last added pin
        }
    }

    fun onAddressSearchTextChange(text: String) {
        viewModelScope.launch {
            state.emit(state.value.copy(placeText = text, placeTextChangedByUser = true))
        }
    }

    fun onTripCancelClick() {
        viewModelScope.launch {
            moveToTripListState()
        }
    }

    fun onTripNameChange(tripName: String) {
        viewModelScope.launch {
            state.emit(state.value.copy(nameText = tripName))
        }
    }

    fun onAddNextStopClick() {
        stops.add(StopDetails(arrivalDate!!, null, selectedPlace!!))
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    isAddressEditEnabled = true,
                    showConfirmAddressButton = false,
                    showExtraFields = false,
                    arrivalDate = null,
                    placeText = ""
                )
            )
        }
    }

    @Immutable
    data class State(
        val predictions: List<AddressPrediction> = listOf(),
        val showExtraFields: Boolean = false,
        val placeId: AddressPrediction.Id? = AddressPrediction.Id(""),
        val placeText: String = "",
        val placeErrorText: String? = null,
        val nameText: String = "",
        val placeTextChangedByUser: Boolean = false,
        val marker: MapMarkerItem? = null,
        val tripMarkers: List<List<MapMarkerItem>> = listOf(),
        val showConfirmAddressButton: Boolean = false,
        val arrivalDate: String? = null,
        val departureDate: String? = null,
        val isAddressEditEnabled: Boolean = false,
        val multiStop: Boolean = false,
        val addingTripStep: AddingTripStep = AddingTripStep.ADD_TRIP_BUTTON,
    ) {
        val isSavingTripEnabled: Boolean
            get() = if (multiStop) {
                placeText.isNotEmpty() && nameText.isNotEmpty() && arrivalDate?.isNotEmpty() == true
            } else {
                placeText.isNotEmpty() && nameText.isNotEmpty() && arrivalDate?.isNotEmpty() == true && departureDate?.isNotEmpty() == true
            }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(showSearchBar: Boolean): MapViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, showSearchBar: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(showSearchBar) as T
            }
        }
    }
}

@Composable
fun mapViewModel(showSearchBar: Boolean): MapViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, MainActivity.ViewModelFactoryProvider::class.java
    ).mapViewModelFactory()

    return viewModel(factory = MapViewModel.provideFactory(factory, showSearchBar))
}
