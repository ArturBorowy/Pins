package com.arturborowy.pins.screen.main.settings

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.BuildConfig
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.TestRobot
import com.arturborowy.pins.screen.main.MainActivity

class SettingsScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : TestRobot(composeTestRule) {

    fun openLicencesScreen() {
        composeTestRule.onNodeWithText(R.string.settings_item_licences)
            .performClick()
    }

    fun checkIfVersionNumberIsCorrect() {
        assertTextBesideText(getString(R.string.settings_item_version), BuildConfig.VERSION_NAME)
    }
}