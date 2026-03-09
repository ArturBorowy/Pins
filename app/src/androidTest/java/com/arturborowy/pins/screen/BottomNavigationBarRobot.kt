package com.arturborowy.pins.screen

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.screen.main.BottomNavItem
import com.arturborowy.pins.screen.main.MainActivity

class BottomNavigationBarRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : TestRobot(composeTestRule) {

    fun openSettingsScreen() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.SETTINGS.name)
            .performClick()
    }
}