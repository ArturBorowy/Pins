package com.arturborowy.pins.ui

import com.arturborowy.pins.domain.Trip
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _back = MutableSharedFlow<Boolean>()
    val back = _back.asSharedFlow()

    private val _sharedFlow =
        MutableSharedFlow<NavigationTarget>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    suspend fun navigateTo(navTarget: NavigationTarget) {
        _sharedFlow.emit(navTarget)
    }

    suspend fun goBack() {
        _back.emit(true)
    }
}

interface NavigationTarget {

    val label: String

    object EDIT_TRIP {

        val TRIP_ID_KEY = "TRIP_ID_KEY"

        val label = create("{$TRIP_ID_KEY}").label

        fun create(tripId: Trip.Id) = create(tripId.value.toString())

        private fun create(tripId: String) = object : NavigationTarget {
            override val label = "EDIT_PIN/$tripId"
        }
    }

    object Licenses : NavigationTarget {
        override val label = "Licenses"
    }

    object ADD_TRIP {
        val SHOW_TRIP_TYPE_BAR_KEY = "SHOW_TRIP_TYPE_BAR_KEY"

        val label = create("{$SHOW_TRIP_TYPE_BAR_KEY}").label

        fun create(showTripTypeBar: Boolean) = create(showTripTypeBar.toString())

        private fun create(showSearchBar: String) = object : NavigationTarget {
            override val label = "ADD_TRIP/$showSearchBar"
        }
    }
}