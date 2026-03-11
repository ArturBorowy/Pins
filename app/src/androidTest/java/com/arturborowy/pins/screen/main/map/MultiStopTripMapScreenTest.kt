package com.arturborowy.pins.screen.main.map

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.arturborowy.pins.BaseComposeTest
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
class MultiStopTripMapScreenTest : BaseComposeTest<MainActivity>() {

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun isAddMultiStopTripShown_whenAddPinFabIsClicked2() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            checkMultiStopButtonIsDisplayed()
        }
    }

    @Test
    fun isKeyboardShown_whenMultiStopBtnIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            checkAddTripFabDoesNotExist()
            clickMultiStopButton()
            checkSearchBarIsDisplayed()
            waitForKeyboard()
        }
    }

    @Test
    fun isAddTripFabIsDisplayed_whenAddressEditBackIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            clickAddressEditBack()
            checkSearchBarDoesNotExist()
            checkAddTripFabIsDisplayed()
        }
    }

    @Test
    fun arePredictionsShown_whenTextIsProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressSearchText()
            checkPredictionsAreDisplayed()
        }
    }

    @Test
    fun arePredictionsHidden_whenPredictionIsChosen() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressSearchText()
            clickFirstPrediction()
            checkPredictionsAreNotDisplayed()
            checkAddressConfirmButtonIsDisplayed()
        }
    }

    @Test
    fun isTripNameCleared_whenAddressEditBackIsClickedTwice() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            clickNextStopButton()
            inputAlternativeAddressAndConfirm()
            inputArrivalDate(2020, 12, 20)

            clickAddressEditBack()
            inputAddressSearchText()
            clickFirstPrediction()
            clickAddressEditBack()
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()

            checkTripNameHintIsDisplayed()
            checkArrivalDateHintIsDisplayed()
        }
    }

    @Test
    fun isTripNameCleared_whenCancelTripIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            clickNextStopButton()
            inputAlternativeAddressAndConfirm()
            inputArrivalDate(2020, 12, 20)

            clickCancelButton()
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()

            checkTripNameHintIsDisplayed()
            checkArrivalDateHintIsDisplayed()
        }
    }

    @Test
    fun isSaveTripBtnDisabled_whenTripNameIsNotProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            inputArrivalDate(2017, 6, 10)
            checkSaveButtonIsNotEnabled()
            checkNextStopButtonIsNotEnabled()
        }
    }

    @Test
    fun isSaveTripBtnDisabled_whenTripNameIsErased() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            clearTripName()
            checkSaveButtonIsNotEnabled()
            checkNextStopButtonIsNotEnabled()
        }
    }

    @Test
    fun isSaveTripBtnDisabled_whenArrivalDateNotProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            inputTripName()
            checkSaveButtonIsNotEnabled()
            checkNextStopButtonIsNotEnabled()
        }
    }

    @Test
    fun isSaveTripBtnEnabled_whenTripNameArrivalDateIsProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            checkSaveButtonIsEnabled()
            checkNextStopButtonIsEnabled()
        }
    }

    @Test
    fun isCancelTripBtnShown_whenPlaceConfirmIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            checkCancelButtonIsDisplayed()
            checkSaveButtonIsDisplayed()
            checkNextStopButtonIsDisplayed()
        }
    }

    @Test
    fun isCancelTripBtnHidden_whenBackIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            inputAddressAndConfirm()
            clickAddressEditBack()
            checkCancelButtonDoesNotExist()
            checkSaveButtonDoesNotExist()
            checkNextStopButtonDoesNotExist()
        }
    }

    @Test
    fun isNetworkUnavailableErrorNotShown_whenAddTripFabIsClicked_whileInternetAvailable() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickMultiStopButton()
            checkInternetUnavailableErrorDoesNotExist()
        }
    }
}
