package com.arturborowy.pins.screen.main.settings.licences

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.screen.BottomNavigationBarRobot
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.screen.main.MockSystemAbstractionModule
import com.arturborowy.pins.screen.main.settings.SettingsScreenRobot
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@HiltAndroidTest
class LicencesScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun areLicencesAdded_whenUserIsOnLicencesScreen() {
        with(BottomNavigationBarRobot(composeTestRule)) {
            openSettingsScreen()
        }

        with(SettingsScreenRobot(composeTestRule)) {
            openLicencesScreen()
        }

        with(LicencesScreenRobot(composeTestRule)) {
            checkIfApacheLicenceIsShown()
            scrollToMitLicence()
            checkIfMitLicenceIsShown()
        }
    }
}
