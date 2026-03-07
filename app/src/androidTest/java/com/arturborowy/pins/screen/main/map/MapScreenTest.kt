package com.arturborowy.pins.screen.main.map

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
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@HiltAndroidTest
class MapScreenTest : BaseComposeTest<MainActivity>() {

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    override val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun isAddMultiStopTripShown_whenAddPinFabIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        //isAddSingleStopTripShown_whenAddPinFabIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_btn_single_stop).assertIsDisplayed()

        //isAddMultiStopTripShown_whenAddPinFabIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop).assertIsDisplayed()
    }
}
