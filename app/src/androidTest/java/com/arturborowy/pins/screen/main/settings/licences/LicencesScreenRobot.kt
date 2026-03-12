package com.arturborowy.pins.screen.main.settings.licences

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.TestRobot
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.ui.composable.LicenceViewTag

class LicencesScreenRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : TestRobot(composeTestRule) {

    fun checkIfApacheLicenceIsShown() {
        composeTestRule.onAllNodesWithTag(LicenceViewTag.LICENCE_NAME)[0]
            .assertTextContains(R.string.licence_apache_2_0_name)
    }

    fun checkIfMitLicenceIsShown() {
        composeTestRule.onAllNodesWithTag(LicenceViewTag.LICENCE_NAME)[1]
            .assertTextContains(R.string.licence_mit_name)
    }

    fun scrollToMitLicence() {
        composeTestRule.onNodeWithTag(LicenceViewTag.LICENCES_LIST)
            .performScrollToIndex(1)
    }
}