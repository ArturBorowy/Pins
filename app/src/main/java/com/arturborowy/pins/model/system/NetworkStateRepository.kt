package com.arturborowy.pins.model.system

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkStateRepository(connectivityManager: ConnectivityManager) {

    private val _hasInternet = MutableStateFlow(false)
    val hasInternet: StateFlow<Boolean> = _hasInternet

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _hasInternet.tryEmit(true)
        }

        override fun onLost(network: Network) {
            _hasInternet.tryEmit(false)
        }
    }

    init {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}