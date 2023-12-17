package com.arturborowy.pins.screen.main

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun checkIfMapIsShown() {
        composeTestRule.onNodeWithText(R.string.main_bottom_nav_label_add).performClick()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.PLACE_QUERY_TO_PREDICT)
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).performClick()

        Espresso.onView(withContentDescription("Google Map")).check(matches(isDisplayed()))
    }

    @Test
    fun checkIfPinIsAddedToDatabase() {
        composeTestRule.onNodeWithText(R.string.main_bottom_nav_label_add).performClick()
        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockModule.PLACE_QUERY_TO_PREDICT)
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).performClick()
        composeTestRule.onNodeWithText(R.string.add_pin_btn_confirm).performClick()

        Espresso.pressBack()

        composeTestRule.onNodeWithText(R.string.main_bottom_nav_label_list).performClick()
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).assertIsDisplayed()
    }

    private fun getString(@StringRes stringResId: Int) =
        composeTestRule.activity.getString(stringResId)

    private fun <
            ActivityT : ComponentActivity,
            ActivityScenarioRuleT : ActivityScenarioRule<ActivityT>
            >
            AndroidComposeTestRule<ActivityScenarioRuleT, ActivityT>.onNodeWithText(
        @StringRes textResId: Int
    ) =
        onNodeWithText(getString(textResId))
}
