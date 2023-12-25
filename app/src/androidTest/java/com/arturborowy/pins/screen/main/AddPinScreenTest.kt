package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.isNotFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class AddPinScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun arePredictionsShown_whenTextIsProvided() {
        composeTestRule.onNodeWithText(R.string.main_bottom_nav_label_add).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockModule.ADDRESS_PREDICION_LABEL).assertExist()
    }

    private fun SemanticsNodeInteractionCollection.assertExist(): SemanticsNodeInteractionCollection {
        fetchSemanticsNodes().forEachIndexed { index, _ ->
            get(index).assertExists()
        }
        return this
    }

    @Test
    fun isGoogleMapShown_whenPredictionIsChosen() {
        composeTestRule.onNodeWithText(R.string.main_bottom_nav_label_add).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()

        Espresso.onView(withContentDescription("Google Map")).check(matches(isDisplayed()))
    }

    @Test
    fun arePredictionsHidden_whenPredictionIsChosen() {
        composeTestRule.onNodeWithText(R.string.main_bottom_nav_label_add).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()

        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).assertExists()
    }

    @Test
    fun isRemovePinHidden_whenPredictionIsChosen() {
        composeTestRule.onNodeWithText(R.string.main_bottom_nav_label_add).performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_btn_remove).assertDoesNotExist()
    }
}
