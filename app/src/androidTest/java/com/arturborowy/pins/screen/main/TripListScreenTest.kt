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
class TripListScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun headerHasEllipsize_whenTripListIsEmpty() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasText(R.string.trip_list_header_empty),
            5000L
        )

        composeTestRule.onNodeWithText(R.string.trip_list_header_empty)
            .assertIsDisplayed()
    }

    @Test
    fun footerIsDisplayed_whenTripListIsEmpty() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasText(R.string.trip_list_footer_empty),
            5000L
        )

        composeTestRule.onNodeWithText(R.string.trip_list_footer_empty)
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

        composeTestRule.onNodeWithText(R.string.trip_list_header)
            .assertIsDisplayed()
    }

    @Test
    fun tripOnListHasCorrectName_whenIsAddedViaTripListScreen() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsTripNameOnTripListCorrect(MOCK_TRIP_NAME)
    }

    @Test
    fun tripOnListHasCorrectDates_whenIsAddedViaTripListScreen() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertAreDatesOnTripListCorrect()
    }

    @Test
    fun tripOnListHasCorrectPlaceName_whenIsAddedViaTripListScreen() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsPlaceNameOnTripListCorrect()
    }

    @Test
    fun tripOnListHasCorrectFlag_whenIsAddedViaTripListScreen() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsFlagOnTripListCorrect()
    }

    @Test
    fun tripOnListHasCorrectName_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsTripNameOnTripListCorrect(MOCK_TRIP_NAME)
    }

    @Test
    fun tripOnListHasCorrectDates_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertAreDatesOnTripListCorrect()
    }

    @Test
    fun tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsPlaceNameOnTripListCorrect()
    }

    @Test
    fun tripOnListHasCorrectFlag_whenIsAddedViaMapScreen() {
        goToTripDetailsInput()
        inputTripDetails()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        assertIsFlagOnTripListCorrect()
    }
}
