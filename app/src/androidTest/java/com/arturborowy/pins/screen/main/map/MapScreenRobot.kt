package com.arturborowy.pins.screen.main.map

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.arturborowy.pins.R
import com.arturborowy.pins.data.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.screen.TestRobot
import com.arturborowy.pins.screen.main.MainActivity
import org.hamcrest.Matchers

@OptIn(ExperimentalTestApi::class)
class MapScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : TestRobot(composeTestRule) {

    fun clickAddTripFab() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.main_bottom_nav_label_add))
            .performClick()
    }

    fun clickSingleStopButton() {
        composeTestRule.onNodeWithText(R.string.add_trip_btn_single_stop).performClick()
    }

    fun checkAddTripButtonsAreDisplayed() {
        composeTestRule.onNodeWithText(R.string.add_trip_btn_single_stop).assertIsDisplayed()
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop).assertIsDisplayed()
    }

    fun checkInternetUnavailableErrorIsDisplayed() {
        composeTestRule.onNodeWithText(R.string.add_trip_error_internet_unavailable)
            .assertIsDisplayed()
    }

    // Address search phase

    fun checkAddTripFabDoesNotExist() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.main_bottom_nav_label_add))
            .assertDoesNotExist()
    }

    fun checkAddTripFabIsDisplayed() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.main_bottom_nav_label_add))
            .assertIsDisplayed()
    }

    fun waitForAddTripFab() {
        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription(getString(R.string.main_bottom_nav_label_add)),
            5000L
        )
    }

    fun checkSearchBarIsDisplayed() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_name)).assertIsDisplayed()
    }

    fun checkSearchBarIsFocused() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_name)).assertIsFocused()
    }

    fun checkSearchBarDoesNotExist() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_name)).assertDoesNotExist()
    }

    fun clickAddressEditBack() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.add_trip_cd_address_editing_back))
            .performClick()
    }

    fun inputAddressSearchText(
        text: String = MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING
    ) {
        composeTestRule.waitUntilExactlyOneExists(
            hasText(getString(R.string.add_trip_hint_name)),
            5000L
        )
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_name))
            .performTextInput(text)
    }

    fun checkPredictionsAreDisplayed() {
        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label).assertExists()
        }
    }

    fun clickFirstPrediction() {
        composeTestRule.waitUntilExactlyOneExists(
            hasText(MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label), 5000L
        )
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label)
            .performClick()
    }

    fun checkPredictionsAreNotDisplayed() {
        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label).assertDoesNotExist()
        }
    }

    fun checkAddressConfirmButtonIsDisplayed() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.add_trip_btn_confirm))
            .assertIsDisplayed()
    }

    fun clickAddressConfirmButton() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.add_trip_btn_confirm))
            .performClick()
    }

    fun inputAddressAndConfirm(
        text: String = MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING
    ) {
        inputAddressSearchText(text)
        clickFirstPrediction()
        clickAddressConfirmButton()
    }

    fun checkInternetUnavailableErrorDoesNotExist() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_error_internet_unavailable))
            .assertDoesNotExist()
    }

    // AddTrip form phase

    fun inputTripName(name: String = MOCK_TRIP_NAME) {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_trip_name))
            .performTextInput(name)
    }

    fun clearTripName() {
        composeTestRule.onNodeWithText(MOCK_TRIP_NAME).performTextReplacement("")
    }

    fun inputArrivalDate(year: Int, month: Int, day: Int) {
        inputDate(getString(R.string.add_trip_hint_arrival_date), year, month, day)
    }

    fun inputDepartureDate(year: Int, month: Int, day: Int) {
        inputDate(getString(R.string.add_trip_hint_departure_date), year, month, day)
    }

    private fun inputDate(fieldText: String, year: Int, month: Int, day: Int) {
        composeTestRule.onNodeWithText(fieldText).performClick()
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(year, month, day))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
    }

    fun clickSaveButton() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_confirm)).performClick()
    }

    fun clickCancelButton() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_cancel)).performClick()
    }

    fun checkTripNameHintIsDisplayed() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_trip_name))
            .assertIsDisplayed()
    }

    fun checkArrivalDateHintIsDisplayed() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_arrival_date))
            .assertIsDisplayed()
    }

    fun checkDepartureDateHintIsDisplayed() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_departure_date))
            .assertIsDisplayed()
    }

    fun checkSaveButtonIsEnabled() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_confirm))
            .assertIsEnabled()
    }

    fun checkSaveButtonIsNotEnabled() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_confirm))
            .assertIsNotEnabled()
    }

    fun checkCancelButtonIsDisplayed() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_cancel))
            .assertIsDisplayed()
    }

    fun checkCancelButtonDoesNotExist() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_cancel))
            .assertDoesNotExist()
    }

    fun checkSaveButtonIsDisplayed() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_confirm))
            .assertIsDisplayed()
    }

    fun checkSaveButtonDoesNotExist() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_confirm))
            .assertDoesNotExist()
    }

    // Multi-stop specific

    fun clickMultiStopButton() {
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop).performClick()
    }

    fun checkMultiStopButtonIsDisplayed() {
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop).assertIsDisplayed()
    }

    fun clickNextStopButton() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_next_stop)).performClick()
    }

    fun checkNextStopButtonIsDisplayed() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_next_stop))
            .assertIsDisplayed()
    }

    fun checkNextStopButtonDoesNotExist() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_next_stop))
            .assertDoesNotExist()
    }

    fun checkNextStopButtonIsEnabled() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_next_stop))
            .assertIsEnabled()
    }

    fun checkNextStopButtonIsNotEnabled() {
        composeTestRule.onNodeWithText(getString(R.string.create_trip_btn_next_stop))
            .assertIsNotEnabled()
    }

    fun waitForKeyboard() {
        composeTestRule.waitUntil(5000L) {
            val inputMethodManager = InstrumentationRegistry.getInstrumentation()
                .targetContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.isAcceptingText
        }
    }

    fun inputAlternativeAddressAndConfirm() {
        inputAddressSearchText(MockPlacesPredictionRepository.ALTERNATIVE_EXPECTED_ADDRESS_PREDICTION_STRING)
        composeTestRule.waitUntilExactlyOneExists(
            hasText(MockPlacesPredictionRepository.ALTERNATIVE_FETCHED_ADDRESS_PREDICTIONS[0].label),
            5000L
        )
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.ALTERNATIVE_FETCHED_ADDRESS_PREDICTIONS[0].label)
            .performClick()
        clickAddressConfirmButton()
    }

    fun replaceAddressFromCurrentPlaceName(currentName: String, newText: String) {
        composeTestRule.onNodeWithText(currentName).performTextReplacement(newText)
    }

    fun replaceAddressSearchText(newText: String) {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_name))
            .performTextReplacement(newText)
    }

    fun waitForPredictions(label: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText(label), 5000L)
    }

    fun checkAddressHintContainsHintText() {
        composeTestRule.onNodeWithText(getString(R.string.add_trip_hint_name))
            .assertTextContains(getString(R.string.add_trip_hint_name))
    }

    fun checkAddressEditBackIsDisplayed() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.add_trip_cd_address_editing_back))
            .assertIsDisplayed()
    }

    companion object {
        private const val MOCK_TRIP_NAME = "trip"
    }
}
