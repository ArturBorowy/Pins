package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.arturborowy.pins.di.ExternalModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coVerify
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
    @OptIn(ExperimentalTestApi::class)
    fun checkIfMapIsShown() {
        composeTestRule.onRoot().assertExists()

        composeTestRule.onNodeWithText("Add new").performClick()
        composeTestRule.onNodeWithText("label").performTextInput(MockModule.PLACE_QUERY_TO_PREDICT)
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).performClick()

        composeTestRule.waitUntilNodeCount(isRoot(), 2, 2000)
    }

    @Test
    fun checkIfPinIsAddedToDatabase() {
        composeTestRule.onNodeWithText("Add new").performClick()
        composeTestRule.onNodeWithText("label").performTextInput(MockModule.PLACE_QUERY_TO_PREDICT)
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).performClick()
        composeTestRule.onNodeWithText("Save").performClick()

        Espresso.pressBack()

        composeTestRule.onNodeWithText("Pins").performClick()
        composeTestRule.onNodeWithText(MockModule.ADDRESS_PREDICION_LABEL).assertIsDisplayed()
    }
}
