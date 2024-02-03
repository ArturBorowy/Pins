package com.arturborowy.pins.screen.main

import android.widget.DatePicker
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Test

@HiltAndroidTest
class MapScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun isSearchBarShown_whenAddPinFabIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name).assertIsDisplayed()
    }

    @Test
    fun isAddPinFabHidden_whenAddPinFabIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertDoesNotExist()
    }

    @Test
    fun isKeyboardShown_whenAddPinFabIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.waitUntil(5000L) {
            isKeyboardShown()
        }
    }

    @Test
    fun isSearchBarHidden_whenAddressEditBackIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name).assertDoesNotExist()
    }

    @Test
    fun isAddPinFabShown_whenAddressEditBackIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertIsDisplayed()
    }

    @Test
    fun arePredictionsShown_whenTextIsProvided() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertExists()
        }
    }

    private fun SemanticsNodeInteractionCollection.assertExist(): SemanticsNodeInteractionCollection {
        fetchSemanticsNodes().forEachIndexed { index, _ ->
            get(index).assertExists()
        }
        return this
    }

    @Test
    fun arePredictionsHidden_whenPredictionIsChosen() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertDoesNotExist()
        }
    }

    @Test
    fun isConfirmBtnShown_whenPredictionIsChosen() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm)
            .assertIsDisplayed()
    }

    @Test
    fun isConfirmBtnHidden_whenConfirmBtnIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm)
            .assertDoesNotExist()
    }

    @Test
    fun isSearchTextCleared_whenAddressEditBackIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)
            .assertDoesNotExist()
    }

    @Test
    fun isTripNameCleared_whenAddressEditBackIsClickedTwice() {
        goToAddTrip()
        addTrip(confirm = false)

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .assertIsDisplayed()
    }

    @Test
    fun isDepartureDateCleared_whenAddressEditBackIsClickedTwice() {
        goToAddTrip()
        addTrip(confirm = false)

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_departure_date)
            .assertIsDisplayed()
    }

    @Test
    fun isArrivalDateCleared_whenAddressEditBackIsClickedTwice() {
        goToAddTrip()
        addTrip(confirm = false)

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_arrival_date)
            .assertIsDisplayed()
    }

    @Test
    fun isTripNameCleared_whenCancelTripIsClicked() {
        goToAddTrip()
        addTrip(confirm = false)

        composeTestRule.onNodeWithText(R.string.create_trip_btn_cancel)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .assertIsDisplayed()
    }

    @Test
    fun isDepartureDateCleared_whenCancelTripIsClicked() {
        goToAddTrip()
        addTrip(confirm = false)

        composeTestRule.onNodeWithText(R.string.create_trip_btn_cancel)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_departure_date)
            .assertIsDisplayed()
    }

    @Test
    fun isArrivalDateCleared_whenCancelTripIsClicked() {
        goToAddTrip()
        addTrip(confirm = false)

        composeTestRule.onNodeWithText(R.string.create_trip_btn_cancel)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_arrival_date)
            .assertIsDisplayed()
    }

    @Test
    fun isSaveTripBtnDisabled_whenTripNameIsNotProvided() {
        goToAddTrip()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_arrival_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2017, 6, 10))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.add_pin_hint_departure_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2020, 11, 30))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsNotEnabled()
    }

    @Test
    fun isSaveTripBtnDisabled_whenTripNameIsErased() {
        goToAddTrip()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.add_pin_hint_arrival_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2017, 6, 10))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.add_pin_hint_departure_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2020, 11, 30))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .performTextReplacement("")

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsNotEnabled()
    }

    @Test
    fun isSaveTripBtnDisabled_whenArrivalDateNotProvided() {
        goToAddTrip()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.add_pin_hint_departure_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2020, 11, 30))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsNotEnabled()
    }

    @Test
    fun isSaveTripBtnDisabled_whenDepartureDateIsNotProvided() {
        goToAddTrip()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.add_pin_hint_arrival_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2017, 6, 10))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsNotEnabled()
    }

    @Test
    fun isSaveTripBtnEnabled_whenTripNameArrivalDepartureDateIsProvided() {
        goToAddTrip()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.add_pin_hint_arrival_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2017, 6, 10))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.add_pin_hint_departure_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2020, 11, 30))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsEnabled()

    }
}
