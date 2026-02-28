package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.model.system.NetworkStateRepository
import com.arturborowy.pins.screen.settings.licenses.LicenceViewTag
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@HiltAndroidTest
class LicensesScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun areLicensesAdded_whenUserIsOnLicensesScreen() {
        goToLicenses()

        test("Apache licence is not shown") {
            composeTestRule.onAllNodesWithTag(LicenceViewTag.LICENCE_NAME)[0]
                .assertTextContains(R.string.licence_apache_2_0_name)
        }

        composeTestRule.onNodeWithTag(LicenceViewTag.LICENCES_LIST)
            .performScrollToIndex(1)

        test("MIT licence is not shown") {
            composeTestRule.onAllNodesWithTag(LicenceViewTag.LICENCE_NAME)[1]
                .assertTextContains(R.string.licence_mit_name)
        }
    }

    private fun goToLicenses() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.SETTINGS.name)
            .performClick()

        composeTestRule.onNodeWithText(R.string.settings_item_Licenses)
            .performClick()
    }
}
