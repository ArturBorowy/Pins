package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.TestRobot
import com.arturborowy.pins.ui.composable.TripViewTag

@OptIn(ExperimentalTestApi::class)
class TripListScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : TestRobot(composeTestRule) {

    fun checkEmptyStateIsDisplayed() {
        composeTestRule.waitUntilExactlyOneExists(
            hasText(getString(R.string.trip_list_header_empty)),
            5000L
        )
        composeTestRule.onNodeWithText(R.string.trip_list_header_empty).assertIsDisplayed()
        composeTestRule.onNodeWithText(R.string.trip_list_footer_empty).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(getString(R.string.main_bottom_nav_label_add))
            .assertIsDisplayed()
    }

    fun checkNonEmptyHeaderIsDisplayed() {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)
        composeTestRule.onNodeWithText(R.string.trip_list_header).assertIsDisplayed()
    }

    fun clickAddTripFab() {
        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription(getString(R.string.main_bottom_nav_label_add)),
            5000L
        )
        composeTestRule.onNodeWithContentDescription(getString(R.string.main_bottom_nav_label_add))
            .performClick()
    }
}
