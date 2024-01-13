package com.arturborowy.pins.screen.main

import android.widget.DatePicker
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.isNotFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.TripViewTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Test

@HiltAndroidTest
class PinListScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun tripOnListHasCorrectName_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_NAME)
            .assertTextContains(MockExternalRepositoryModule.TRIP_NAME)
    }

    @Test
    fun tripOnListHasCorrectDates_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_DATES)
            .assertTextContains("10 Jun 2017 - 30 Nov 2020")
    }

    @Test
    fun tripOnListHasCorrectPlaceName_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_PLACE)
            .assertTextContains(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
    }

    @Test
    fun tripOnListHasCorrectFlag_whenIsAddedViaMapScreen() {
        goToAddTrip()
        addTrip()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.onNodeWithContentDescription(MockExternalRepositoryModule.country.label)
            .assertIsDisplayed()
    }

    private fun addTrip() {
        composeTestRule.onNodeWithText(R.string.add_pin_hint_trip_name)
            .performTextInput(MockExternalRepositoryModule.TRIP_NAME)

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
            .performTextInput(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()
    }
}
