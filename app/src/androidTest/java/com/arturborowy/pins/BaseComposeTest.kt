package com.arturborowy.pins

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.arturborowy.pins.model.db.AppDatabase
import com.ultimatelogger.android.output.ALogInitializer
import com.ultimatelogger.multiplatform.tag.TagSettings
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class BaseComposeTest<ActivityT : ComponentActivity> {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    abstract val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ActivityT>, ActivityT>

    @Inject
    lateinit var appDatabase: AppDatabase

    @Before
    open fun init() {
        initLogger()
        hiltRule.inject()
    }

    private fun initLogger() {
        val shouldLog = BuildConfig.DEBUG
        val defaultTagSettings = TagSettings(
            shouldLogFileNameAndLineNum = true,
            shouldLogClassName = true,
            shouldLogMethodName = true
        )

        ALogInitializer.init(shouldLog, defaultTagSettings)
    }

    @After
    open fun tearDown() {
        appDatabase.clearAllTables()
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

    protected fun <
            ActivityT : ComponentActivity,
            ActivityScenarioRuleT : ActivityScenarioRule<ActivityT>
            >
            AndroidComposeTestRule<ActivityScenarioRuleT, ActivityT>.onNodeWithContentDescription(
        @StringRes textResId: Int
    ) =
        onNodeWithContentDescription(getString(textResId))
}