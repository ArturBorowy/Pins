package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.screen.BottomNavigationBarRobot
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class TripListScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun headerHasEllipsize_whenTripListIsEmpty() {
        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
        }

        with(TripListScreenRobot(composeTestRule)) {
            checkEmptyStateIsDisplayed()
        }
    }

    @Test
    fun headerHasNotEllipsize_whenTripListIsNotEmpty() {
        goToSingleStopTripDetailsInput()
        inputSingleStopTripDetails()

        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
        }

        with(TripListScreenRobot(composeTestRule)) {
            checkNonEmptyHeaderIsDisplayed()
        }
    }

    @Test
    fun singleStopTripOnListHasCorrectName_whenIsAddedViaTripListScreen() {
        addSingleStopTripViaTripList()

        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
        }

        //tripOnListHasCorrectName_whenIsAddedViaTripListScreen
        assertIsTripNameOnTripListCorrect(MOCK_TRIP_NAME)

        //tripOnListHasCorrectDates_whenIsAddedViaTripListScreen
        assertAreDatesOnTripListCorrect()

        //tripOnListHasCorrectPlaceName_whenIsAddedViaTripListScreen
        assertIsPlaceNameOnTripListCorrect()

        //tripOnListHasCorrectFlag_whenIsAddedViaTripListScreen
        assertIsFlagOnTripListCorrect()
    }

    @Test
    fun singleStopTripOnListHasCorrectName_whenIsAddedViaMapScreen() {
        goToSingleStopTripDetailsInput()
        inputSingleStopTripDetails()

        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
        }

        //tripOnListHasCorrectName_whenIsAddedViaMapScreen
        assertIsTripNameOnTripListCorrect(MOCK_TRIP_NAME)

        //tripOnListHasCorrectDates_whenIsAddedViaMapScreen
        assertAreDatesOnTripListCorrect()

        //tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen
        assertIsPlaceNameOnTripListCorrect()

        //tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen
        assertIsFlagOnTripListCorrect()
    }

    @Test
    fun multiStopTripOnListHasCorrectName_whenIsAddedViaTripListScreen() {
        addMultiStopTripViaTripList()

        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
        }

        //tripOnListHasCorrectName_whenIsAddedViaTripListScreen
        assertIsTripNameOnTripListCorrect(MOCK_TRIP_NAME)

        //tripOnListHasCorrectFlag_whenIsAddedViaTripListScreen
        assertIsFlagOnTripListCorrect()

        assertArePlaceNamesOnTripListCorrect()

        assertAreDatesOnTripListCorrect2()
    }

    @Test
    fun multiStopTripOnListHasCorrectName_whenIsAddedViaMapScreen() {
        goToMultiStopTripDetailsInput()
        inputMultiStopTripDetails()

        with(BottomNavigationBarRobot(composeTestRule)) {
            openTripListScreen()
        }

        //tripOnListHasCorrectName_whenIsAddedViaMapScreen
        assertIsTripNameOnTripListCorrect(MOCK_TRIP_NAME)

        //tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen
        assertIsFlagOnTripListCorrect()

        assertArePlaceNamesOnTripListCorrect()

        assertAreDatesOnTripListCorrect2()
    }
}
