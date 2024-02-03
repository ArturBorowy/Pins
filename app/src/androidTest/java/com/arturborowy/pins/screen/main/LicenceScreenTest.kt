package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.settings.licences.LicenceViewTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class LicenceScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun isApacheLicenceAdded_whenUserIsOnLicenceScreen() {
        goToLicences()

        composeTestRule.onAllNodesWithTag(LicenceViewTag.LICENCE_NAME)[0]
            .assertTextContains(R.string.licence_apache_2_0_name)
    }

    @Test
    fun isMitLicenceAdded_whenUserIsOnLicenceScreen() {
        goToLicences()

        composeTestRule.onAllNodesWithTag(LicenceViewTag.LICENCE_NAME)[1]
            .performScrollTo()

        composeTestRule.onAllNodesWithTag(LicenceViewTag.LICENCE_NAME)[1]
            .assertTextContains(R.string.licence_mit_name)
    }

    private fun goToLicences() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.SETTINGS.name)
            .performClick()

        composeTestRule.onNodeWithText(R.string.settings_item_licences)
            .performClick()
    }
}
