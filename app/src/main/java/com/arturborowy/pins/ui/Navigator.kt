package com.arturborowy.pins.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _sharedFlow =
        MutableSharedFlow<NavigationTarget>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(navTarget: NavigationTarget) {
        _sharedFlow.tryEmit(navTarget)
    }
}

interface NavigationTarget {

    val label: String

    object EDIT_PIN {

        val PLACE_ID_KEY = "PLACE_ID_KEY"

        val label = create("{$PLACE_ID_KEY}").label

        fun create(placeId: Long) = create(placeId.toString())

        private fun create(placeId: String) = object : NavigationTarget {
            override val label = "EDIT_PIN/$placeId"
        }
    }
}