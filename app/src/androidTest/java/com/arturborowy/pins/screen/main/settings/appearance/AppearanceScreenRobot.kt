package com.arturborowy.pins.screen.main.settings.appearance

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.TestRobot
import com.arturborowy.pins.screen.main.MainActivity

class AppearanceScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : TestRobot(composeTestRule) {

    fun checkIfOnAppearanceScreen() {
        composeTestRule.onNodeWithText(R.string.settings_item_appearance).assertExists()
    }
}
