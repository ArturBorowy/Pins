package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.isNotFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class MapScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun isSearchBarShown_whenAddPinFabIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name).assertIsDisplayed()
    }

    @Test
    fun isAddPinFabHidden_whenAddPinFabIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertDoesNotExist()
    }

    @Test
    fun isSearchBarHidden_whenAddressEditBackIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name).assertDoesNotExist()
    }

    @Test
    fun isAddPinFabShown_whenAddressEditBackIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.add_pin_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertIsDisplayed()
    }

    @Test
    fun arePredictionsShown_whenTextIsProvided() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
            .assertExist()
    }

    private fun SemanticsNodeInteractionCollection.assertExist(): SemanticsNodeInteractionCollection {
        fetchSemanticsNodes().forEachIndexed { index, _ ->
            get(index).assertExists()
        }
        return this
    }

    @Test
    fun arePredictionsHidden_whenPredictionIsChosen() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()

        composeTestRule.onNodeWithText(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
            .assertExists()
    }

    @Test
    fun isConfirmBtnShown_whenPredictionIsChosen() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_pin_hint_name)
            .performTextInput(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
        composeTestRule.onAllNodesWithText(MockExternalRepositoryModule.ADDRESS_PREDICION_LABEL)
            .filter(isNotFocused())[0].performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_pin_btn_confirm)
            .assertIsDisplayed()
    }
}
