package com.arturborowy.pins.screen.internetunavailable

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.screen.BottomNavigationBarRobot
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.screen.main.MockSystemAbstractionModule
import com.arturborowy.pins.screen.main.TripListScreenRobot
import com.arturborowy.pins.screen.main.map.MapScreenRobot
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@HiltAndroidTest
class InternetUnavailableTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @BindValue
    @JvmField
    val networkStateRepository = mockk<NetworkStateRepository>().apply {
        every { hasInternet } returns MutableStateFlow(false)
    }

    @Test
    fun isNetworkUnavailableErrorShown_whenAddTripFabIsClicked_onTripList() {
        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
        }

        with(TripListScreenRobot(composeTestRule)) {
            clickAddTripFab()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickSingleStopButton()
            checkInternetUnavailableErrorIsDisplayed()
        }
    }

    @Test
    fun isNetworkUnavailableErrorShown_whenAddTripFabIsClicked_onMap() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            checkInternetUnavailableErrorIsDisplayed()
        }
    }
}
