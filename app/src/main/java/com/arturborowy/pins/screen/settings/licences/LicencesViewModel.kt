package com.arturborowy.pins.screen.settings.licences

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.domain.licences.Licence
import com.arturborowy.pins.domain.licences.LicencesInteractor
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LicencesViewModel @Inject constructor(
    private val licencesInteractor: LicencesInteractor,
) : BaseViewModel() {

    val state = MutableStateFlow(State())

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModelScope.launch {
            state.emit(state.value.copy(licences = licencesInteractor.getLicences()))
        }
    }

    data class State(val licences: List<Licence> = listOf())
}