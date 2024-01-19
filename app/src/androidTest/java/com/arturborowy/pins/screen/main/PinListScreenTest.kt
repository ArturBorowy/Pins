package com.arturborowy.pins.screen.main

import android.widget.DatePicker
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.geocoding.MockGeocodingRepository
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.ui.composable.TripViewTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class PinListScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun tripOnListHasCorrectName_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_NAME)
            .assertTextContains(MOCK_TRIP_NAME)
    }

    @Test
    fun tripOnListHasCorrectDates_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_DATES)
            .assertTextContains("10 Jun 2017 - 30 Nov 2020")
    }

    @Test
    fun tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_PLACE)
            .assertTextContains(MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName)
    }

    @Test
    fun tripOnListHasCorrectFlag_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithContentDescription(MockGeocodingRepository.GEOCODED_COUNTRY.label)
            .assertIsDisplayed()
    }

    private fun addTrip() {
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

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).performClick()
    }

    private fun goToAddTrip() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()
    }

    companion object {
        private const val MOCK_TRIP_NAME = "trip"
    }
}
