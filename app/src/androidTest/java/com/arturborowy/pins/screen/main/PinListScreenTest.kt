package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.model.remote.geocoding.MockGeocodingRepository
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.ui.composable.TripViewTag
import dagger.hilt.android.testing.HiltAndroidTest
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
}
