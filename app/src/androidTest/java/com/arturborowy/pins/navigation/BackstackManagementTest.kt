package com.arturborowy.pins.navigation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.screen.BottomNavigationBarRobot
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.screen.main.MockSystemAbstractionModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class BackstackManagementTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun appIsInBackground_whenPressingBackOnTripListScreen() {
        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
            goBack()
        }

        assertAppIsInBackground()
    }

    @Test
    fun appIsInBackground_whenPressingBackOnSettingsScreen() {
        with(BottomNavigationBarRobot(composeTestRule)) {
            openSettingsScreen()
            goBack()
        }

        assertAppIsInBackground()
    }

    @Test
    fun appIsInBackground_whenPressingBackAfterManyMovesThroughBottomBarNavigation() {
        with(BottomNavigationBarRobot(composeTestRule)) {
            openSettingsScreen()
            openTripListScreen()
            openMapScreen()
            openSettingsScreen()
            openTripListScreen()
            openMapScreen()
            openSettingsScreen()
            openTripListScreen()
            openMapScreen()
            goBack()
        }

        assertAppIsInBackground()
    }
}
