package com.arturborowy.pins.screen.pinslist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.model.system.LocaleRepository
import com.arturborowy.pins.model.system.ResourcesRepository
import com.arturborowy.pins.ui.NavigationTarget
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PinsListViewModel @Inject constructor(
    private val navigator: Navigator,
    private val placesInteractor: PlacesInteractor,
    private val resourcesRepository: ResourcesRepository,
    private val localeRepository: LocaleRepository
) : BaseViewModel() {

    val state = MutableStateFlow(State())

    override fun onResume(owner: LifecycleOwner) {
        viewModelScope.launch {
            val dateFormatter = SimpleDateFormat("dd MMM yyyy", localeRepository.locale)

            val places = placesInteractor.getPlaces()
                .map {
                    TripSingleStop(
                        it.id,
                        it.name,
                        it.locationName,
                        if (it.departureDate == null) {
                            dateFormatter.format(Date(it.arrivalDate))
                        } else {
                            resourcesRepository.getString(
                                R.string.pin_list_pattern_date_range,
                                dateFormatter.format(Date(it.arrivalDate)),
                                dateFormatter.format(Date(it.departureDate)),
                            )
                        },
                        it.country
                    )
                }
            state.emit(state.value.copy(tripDetails = places, isLoading = false))
        }
    }

    fun onAddressClick(tripSingleStop: TripSingleStop) {
        navigator.navigateTo(NavigationTarget.EDIT_PIN.create(tripSingleStop.id))
    }

    data class State(
        val isLoading: Boolean = true,
        val tripDetails: List<TripSingleStop> = listOf(),
    )
}