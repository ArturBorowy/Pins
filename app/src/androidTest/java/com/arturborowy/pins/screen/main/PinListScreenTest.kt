package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.TripViewTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class PinListScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun headerHasEllipsize_whenTripListIsEmpty() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasText(R.string.pin_list_header_empty),
            5000L
        )

        composeTestRule.onNodeWithText(R.string.pin_list_header_empty)
            .assertIsDisplayed()
    }

    @Test
    fun footerIsDisplayed_whenTripListIsEmpty() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasText(R.string.pin_list_footer_empty),
            5000L
        )

        composeTestRule.onNodeWithText(R.string.pin_list_footer_empty)
            .assertIsDisplayed()
    }

    @Test
    fun addTripBtnIsDisplayed_whenTripListIsEmpty() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription(R.string.main_bottom_nav_label_add),
            5000L
        )

        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertIsDisplayed()
    }

    @Test
    fun headerHasNotEllipsize_whenTripListIsNotEmpty() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithText(R.string.pin_list_header)
            .assertIsDisplayed()
    }

    @Test
    fun tripOnListHasCorrectName_whenIsAddedViaPinListScreen() {
        goToTripAddingViaPinListScreen()
        inputTripPlace()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsTripNameOnPinListCorrect()
    }

    @Test
    fun tripOnListHasCorrectDates_whenIsAddedViaPinListScreen() {
        goToTripAddingViaPinListScreen()
        inputTripPlace()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertAreDatesOnPinListCorrect()
    }

    @Test
    fun tripOnListHasCorrectPlaceName_whenIsAddedViaPinListScreen() {
        goToTripAddingViaPinListScreen()
        inputTripPlace()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsPlaceNameOnPinListCorrect()
    }

    @Test
    fun tripOnListHasCorrectFlag_whenIsAddedViaPinListScreen() {
        goToTripAddingViaPinListScreen()
        inputTripPlace()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsFlagOnPinListCorrect()
    }

    @Test
    fun tripOnListHasCorrectName_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsTripNameOnPinListCorrect()
    }

    @Test
    fun tripOnListHasCorrectDates_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertAreDatesOnPinListCorrect()
    }

    @Test
    fun tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsPlaceNameOnPinListCorrect()
    }

    @Test
    fun tripOnListHasCorrectFlag_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsFlagOnPinListCorrect()
    }
}
