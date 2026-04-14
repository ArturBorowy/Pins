package com.arturborowy.pins.screen.triplist

import androidx.compose.runtime.Immutable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.R
import com.arturborowy.pins.data.system.LocaleRepository
import com.arturborowy.pins.data.system.ResourcesRepository
import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.domain.Trip
import com.arturborowy.pins.ui.NavigationTarget
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class TripsListViewModel @Inject constructor(
    private val navigator: Navigator,
    private val placesInteractor: PlacesInteractor,
    private val resourcesRepository: ResourcesRepository,
    private val localeRepository: LocaleRepository
) : BaseViewModel() {

    val state = MutableStateFlow(State())

    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", localeRepository.locale)

    override fun onResume(owner: LifecycleOwner) {
        viewModelScope.launch {
            val places = placesInteractor.getPlaces()
                .map { createTripVM(it) }
            state.emit(state.value.copy(tripDetails = places, isLoading = false))
        }
    }

    private fun createTripVM(trip: Trip): TripListItem {
        return TripListItem(
            trip.id,
            trip.name,
            trip.stops.map { stop ->
                TripListItemStopItem(
                    stop.placeDetailsWithCountry.locationName,
                    if (stop.departureDate == null) {
                        dateFormatter.format(stop.arrivalDate)
                    } else {
                        resourcesRepository.getString(
                            R.string.trip_list_pattern_date_range,
                            dateFormatter.format(stop.arrivalDate),
                            dateFormatter.format(stop.departureDate),
                        )
                    },
                    stop.placeDetailsWithCountry.country
                )
            }
        )
    }

    fun onEditTripClick(tripListItem: TripListItem) {
        viewModelScope.launch {
            navigator.navigateTo(NavigationTarget.EDIT_TRIP.create(tripListItem.id))
        }
    }

    fun onAddTripClick() {
        viewModelScope.launch {
            navigator.navigateTo(NavigationTarget.ADD_TRIP.create(true))
        }
    }

    @Immutable
    data class State(
        val isLoading: Boolean = true,
        val tripDetails: List<TripListItem> = listOf(),
    )
}
