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

    abstract val label: String

    object EDIT_PIN : NavigationTarget {
        override val label = "EDIT_PIN"
    }
}