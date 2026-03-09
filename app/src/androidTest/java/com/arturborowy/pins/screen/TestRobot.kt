package com.arturborowy.pins.screen

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.screen.main.MainActivity

abstract class TestRobot(val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    // assertion of buttons and clicking them
    fun clickTextButton(text: String) = composeTestRule.onNode(hasTextExactly(text)).performClick()

    fun clickIconButton(description: String) = composeTestRule.onNode(
        hasContentDescription(description).and(
            hasClickAction()
        )
    ).performClick()

    protected fun getString(@StringRes stringResId: Int) =
        composeTestRule.activity.getString(stringResId)

    protected fun SemanticsNodeInteraction.assertTextContains(@StringRes textResId: Int) =
        assertTextContains(getString(textResId))

    protected fun <ActivityT : ComponentActivity, ActivityScenarioRuleT : ActivityScenarioRule<ActivityT>>
            AndroidComposeTestRule<ActivityScenarioRuleT, ActivityT>.onNodeWithText(
        @StringRes textResId: Int
    ) =
        onNodeWithText(getString(textResId))

    fun goBack() = clickIconButton("Back button")

    fun assertIconButton(description: String) =
        composeTestRule.onNode(hasContentDescription(description).and(hasClickAction()))
            .assertExists()

    fun assertTextButton(text: String) =
        composeTestRule.onNode(hasText(text).and(hasClickAction())).assertExists()

    fun assertTextButtonWithIcon(text: String, description: String) =
        composeTestRule.onNode(
            hasText(text)
                .and(hasClickAction())
                .and(
                    hasAnySibling(
                        hasClickAction()
                            .and(hasContentDescription(description))
                    )
                )
        ).assertExists()

    fun assertImage(description: String) =
        composeTestRule.onNode(hasContentDescription(description)).assertExists()

    // text assertions
    fun assertText(text: String, ignoreCase: Boolean = false, substring: Boolean = false) =
        composeTestRule.onNode(hasText(text, ignoreCase = ignoreCase, substring = substring))
            .assertExists()

    fun assertDoesNotExistText(
        text: String, ignoreCase: Boolean = false, substring: Boolean = false
    ) = composeTestRule.onNode(hasText(text, ignoreCase = ignoreCase, substring = substring))
        .assertDoesNotExist()

    fun assertTextBesideImage(text: String, description: String) {
        composeTestRule.onNode(
            hasText(text).and(
                hasAnySibling(hasContentDescription(description))
            )
        ).assertExists()
    }

    fun assertTextBesideText(siblingText: String, text: String) =
        composeTestRule.onNode(
            hasText(text)
                .and(hasAnySibling(hasText(siblingText)))
        )
            .assertExists()

    @OptIn(ExperimentalTestApi::class)
    fun waitFor(matcher: SemanticsMatcher) = composeTestRule.waitUntilExactlyOneExists(matcher)
}