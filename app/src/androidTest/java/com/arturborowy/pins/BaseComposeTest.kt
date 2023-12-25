package com.arturborowy.pins

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class BaseComposeTest<ActivityT : ComponentActivity> {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    abstract val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ActivityT>, ActivityT>

    @Before
    open fun init() {
        hiltRule.inject()
    }

    protected fun getString(@StringRes stringResId: Int) =
        composeTestRule.activity.getString(stringResId)

    protected fun <
            ActivityT : ComponentActivity,
            ActivityScenarioRuleT : ActivityScenarioRule<ActivityT>
            >
            AndroidComposeTestRule<ActivityScenarioRuleT, ActivityT>.onNodeWithText(
        @StringRes textResId: Int
    ) =
        onNodeWithText(getString(textResId))
}