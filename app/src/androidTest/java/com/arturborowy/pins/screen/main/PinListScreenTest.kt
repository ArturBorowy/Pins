package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.filter
import androidx.compose.ui.test.isNotFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class PinListScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun pinAppearsOnList_whenIsConfirmedOnPinAdd() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add).performClick()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()
        composeTestRule.onNodeWithText(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).assertExists()
    }
}
