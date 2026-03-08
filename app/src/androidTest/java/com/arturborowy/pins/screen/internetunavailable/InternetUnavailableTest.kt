package com.arturborowy.pins.screen.internetunavailable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.screen.main.MockSystemAbstractionModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@HiltAndroidTest
class InternetUnavailableTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun isNetworkUnavailableErrorShown_whenAddTripFabIsClicked_onTripList() {
        goToSingleStopTripAddingViaTripListScreen()

        composeTestRule.onNodeWithText(R.string.add_trip_error_internet_unavailable)
            .assertIsDisplayed()
    }

    @Test
    fun isNetworkUnavailableErrorShown_whenAddTripFabIsClicked_onMap() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithText(R.string.add_trip_btn_single_stop)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_error_internet_unavailable)
            .assertIsDisplayed()
    }

    @BindValue
    @JvmField
    val networkStateRepository = mockk<NetworkStateRepository>().apply {
        every { hasInternet } returns MutableStateFlow(false)
    }
}
