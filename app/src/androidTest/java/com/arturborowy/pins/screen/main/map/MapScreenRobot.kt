package com.arturborowy.pins.screen.main.map

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.TestRobot
import com.arturborowy.pins.screen.main.MainActivity

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
}
