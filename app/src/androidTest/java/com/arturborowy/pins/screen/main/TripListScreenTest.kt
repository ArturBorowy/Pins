package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.model.system.NetworkStateRepository
import com.arturborowy.pins.ui.composable.TripViewTag
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
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasText(R.string.trip_list_header_empty),
            5000L
        )

        //headerHasEllipsize_whenTripListIsEmpty
        composeTestRule.onNodeWithText(R.string.trip_list_header_empty)
            .assertIsDisplayed()

        //footerIsDisplayed_whenTripListIsEmpty
        composeTestRule.onNodeWithText(R.string.trip_list_footer_empty)
            .assertIsDisplayed()

        //addTripBtnIsDisplayed_whenTripListIsEmpty
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertIsDisplayed()
    }

    @Test
    fun headerHasNotEllipsize_whenTripListIsNotEmpty() {
        goToSingleStopTripDetailsInput()
        inputSingleStopTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithText(R.string.trip_list_header)
            .assertIsDisplayed()
    }

    @Test
    fun singleStopTripOnListHasCorrectName_whenIsAddedViaTripListScreen() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

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

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

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

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

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

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        //tripOnListHasCorrectName_whenIsAddedViaMapScreen
        assertIsTripNameOnTripListCorrect(MOCK_TRIP_NAME)

        //tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen
        assertIsFlagOnTripListCorrect()

        assertArePlaceNamesOnTripListCorrect()

        assertAreDatesOnTripListCorrect2()
    }
}
