package com.arturborowy.pins.ui

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

        val PLACE_ID_KEY = "PLACE_ID_KEY"

        val label = create("{$PLACE_ID_KEY}").label

        fun create(placeId: Long) = create(placeId.toString())

        private fun create(placeId: String) = object : NavigationTarget {
            override val label = "EDIT_PIN/$placeId"
        }
    }

    object LICENCES : NavigationTarget {
        override val label = "LICENCES"
    }

    object ADD_TRIP {
        val SHOW_SEARCH_BAR_KEY = "SHOW_SEARCH_BAR_KEY"

        val label = create("{$SHOW_SEARCH_BAR_KEY}").label

        fun create(showSearchBar: Boolean) = create(showSearchBar.toString())

        private fun create(showSearchBar: String) = object : NavigationTarget {
            override val label = "ADD_TRIP/$showSearchBar"
        }
    }
}