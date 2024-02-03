package com.arturborowy.pins.screen.settings

import com.arturborowy.pins.model.system.BuildInfoRepository
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val buildInfoRepository: BuildInfoRepository,
) : BaseViewModel() {

    val state = MutableStateFlow(State(buildInfoRepository.buildVersion))

    data class State(val versionNumber: String)
}