package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.BuildConfig
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.model.system.NetworkStateRepository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class SettingsScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun isVersionNumberCorrect_whenUserIsOnSettingsScreen() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.SETTINGS.name)
            .performClick()

        composeTestRule.onNodeWithText(BuildConfig.VERSION_NAME).assertIsDisplayed()
    }
}
