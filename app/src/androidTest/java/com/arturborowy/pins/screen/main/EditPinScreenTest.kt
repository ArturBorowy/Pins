package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.isNotFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class EditPinScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun pinDoesNotAppearOnList_whenIsRemovedOnEditPin() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()
        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm).performClick()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).performClick()
        composeTestRule.onNodeWithText(R.string.add_pin_btn_remove).performClick()

        Espresso.pressBack()

        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).assertIsNotDisplayed()
    }
}