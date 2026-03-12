package com.arturborowy.pins.screen.main.edittrip

import android.widget.DatePicker
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.R
import com.arturborowy.pins.data.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.screen.TestRobot
import com.arturborowy.pins.screen.main.BottomNavItem
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.ui.composable.TripViewTag
import org.hamcrest.Matchers

@OptIn(ExperimentalTestApi::class)
class EditTripScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : TestRobot(composeTestRule) {

    // Navigation

    fun goToTripList() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()
    }

    fun chooseTripToEdit(tripName: String = MOCK_TRIP_NAME) {
        val cd = composeTestRule.activity.getString(R.string.trip_list_cd_edit, tripName)
        composeTestRule.waitUntilExactlyOneExists(hasContentDescription(cd), 5000L)
        composeTestRule.onNodeWithContentDescription(cd).performClick()
        composeTestRule.waitUntilExactlyOneExists(
            hasText(getString(R.string.edit_trip_btn_save_changes)), 5000L
        )
    }

    // Form interactions

    fun inputDate(existingDateText: String, year: Int, month: Int, day: Int) {
        composeTestRule.onNodeWithText(existingDateText).performClick()
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(year, month, day))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
    }

    fun replaceTripName(newName: String) {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_trip_name))
            .performTextReplacement(newName)
    }

    fun clickSaveChangesButton() {
        composeTestRule.onNodeWithText(getString(R.string.edit_trip_btn_save_changes))
            .performClick()
    }

    fun clickFirstSaveChangesButton() {
        composeTestRule.onAllNodes(hasText(getString(R.string.edit_trip_btn_save_changes)))
            .onFirst()
            .performClick()
    }

    fun clickDeleteButton() {
        composeTestRule.onNodeWithText(getString(R.string.edit_trip_btn_delete)).performClick()
    }

    fun clickNextStopNavigationButton() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.edit_trip_cd_next_stop))
            .performClick()
    }

    fun clickPreviousStopNavigationButton() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.edit_trip_cd_previous_stop))
            .performClick()
    }

    // Wait helpers

    fun waitForSaveChangesButtonCount(count: Int, timeout: Long = 5000L) {
        composeTestRule.waitUntilNodeCount(
            hasText(getString(R.string.edit_trip_btn_save_changes)), count, timeout
        )
    }

    fun waitForSaveChangesButtonToAppear() {
        composeTestRule.waitUntilExactlyOneExists(
            hasText(getString(R.string.edit_trip_btn_save_changes)), 5000L
        )
    }

    fun waitForDateText(dateText: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText(dateText), 5000L)
    }

    // Assertions

    fun checkTripNameIsDisplayed(name: String) =
        composeTestRule.onNodeWithText(name).assertIsDisplayed()

    fun checkPlaceNameIsDisplayed(name: String) =
        composeTestRule.onNodeWithText(name).assertIsDisplayed()

    fun checkPlaceNameIsNotDisplayed(name: String) =
        composeTestRule.onNodeWithText(name).assertIsNotDisplayed()

    fun checkDateIsDisplayed(date: String) =
        composeTestRule.onNodeWithText(date).assertIsDisplayed()

    fun checkDateIsNotDisplayed(date: String) =
        composeTestRule.onNodeWithText(date).assertIsNotDisplayed()

    fun checkTripNameFieldDoesNotExist() =
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_trip_name))
            .assertDoesNotExist()

    fun checkArrivalDateFieldDoesNotExist() =
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_arrival_date))
            .assertDoesNotExist()

    fun checkDepartureDateFieldDoesNotExist() =
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_departure_date))
            .assertDoesNotExist()

    fun checkDeleteButtonIsDisplayed() =
        composeTestRule.onNodeWithText(getString(R.string.edit_trip_btn_delete)).assertIsDisplayed()

    fun checkDeleteButtonDoesNotExist() =
        composeTestRule.onNodeWithText(getString(R.string.edit_trip_btn_delete))
            .assertDoesNotExist()

    fun checkSaveChangesButtonIsDisplayed() =
        composeTestRule.onNodeWithText(getString(R.string.edit_trip_btn_save_changes))
            .assertIsDisplayed()

    fun checkSaveChangesButtonDoesNotExist() =
        composeTestRule.onNodeWithText(getString(R.string.edit_trip_btn_save_changes))
            .assertDoesNotExist()

    fun checkTripDatesTagDoesNotExist() =
        composeTestRule.onNodeWithTag(TripViewTag.TRIP_DATES).assertDoesNotExist()

    fun checkTripNameTagDoesNotExist() =
        composeTestRule.onNodeWithTag(TripViewTag.TRIP_NAME).assertDoesNotExist()

    fun checkTripPlaceTagDoesNotExist() =
        composeTestRule.onNodeWithTag(TripViewTag.TRIP_PLACE).assertDoesNotExist()

    fun checkFirstStopDataIsDisplayed() {
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("10 Jun 2017").assertIsDisplayed()
    }

    fun checkFirstStopDataIsNotDisplayed() {
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName)
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithText("10 Jun 2017").assertIsNotDisplayed()
    }

    fun checkSecondStopDataIsDisplayed() {
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.ALTERNATIVE_FETCHED_PLACE_DETAILS.locationName)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("20 Dec 2020").assertIsDisplayed()
    }

    fun checkSecondStopDataIsNotDisplayed() {
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.ALTERNATIVE_FETCHED_PLACE_DETAILS.locationName)
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithText("20 Dec 2020").assertIsNotDisplayed()
    }

    fun checkMultiStopDatesOnTripList(date1: String, date2: String) {
        composeTestRule.waitUntilNodeCount(hasTestTag(TripViewTag.TRIP_DATES), 2, 5000L)
        composeTestRule.onAllNodesWithTag(TripViewTag.TRIP_DATES)[0].assertTextContains(date1)
        composeTestRule.onAllNodesWithTag(TripViewTag.TRIP_DATES)[1].assertTextContains(date2)
    }

    companion object {
        const val MOCK_TRIP_NAME = "trip"
    }
}
