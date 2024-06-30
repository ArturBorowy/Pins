package com.arturborowy.pins.screen.settings.Licenses

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.domain.Licenses.Licence
import com.arturborowy.pins.domain.Licenses.LicensesInteractor
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LicensesViewModel @Inject constructor(
    private val LicensesInteractor: LicensesInteractor,
) : BaseViewModel() {

    val state = MutableStateFlow(State())

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModelScope.launch {
            state.emit(state.value.copy(Licenses = LicensesInteractor.getLicenses()))
        }
    }

    data class State(val Licenses: List<Licence> = listOf())
}