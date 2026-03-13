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
class SingleStopTripMapScreenTest : BaseComposeTest<MainActivity>() {

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun isSearchBarDisplayedAndFocused_whenSingleStopBtnIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            checkAddTripFabDoesNotExist()
            clickSingleStopButton()
            checkSearchBarIsDisplayed()
            checkSearchBarIsFocused()
        }
    }

    @Test
    fun isAddTripFabDisplayed_whenAddressEditBackIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            clickAddressEditBack()
            checkSearchBarDoesNotExist()
            checkAddTripFabIsDisplayed()
        }
    }

    @Test
    fun arePredictionsShown_whenTextIsProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressSearchText()
            checkPredictionsAreDisplayed()
        }
    }

    @Test
    fun arePredictionsHidden_whenPredictionIsChosen() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressSearchText()
            clickFirstPrediction()
            checkPredictionsAreNotDisplayed()
            checkAddressConfirmButtonIsDisplayed()
        }
    }

    @Test
    fun isFormCleared_whenAddressEditBackIsClickedTwice() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()

            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)

            clickAddressEditBack()

            inputAddressSearchText()
            clickFirstPrediction()

            clickAddressEditBack()

            clickAddTripFab()
            clickSingleStopButton()

            inputAddressAndConfirm()
            checkTripNameHintIsDisplayed()
            checkDepartureDateHintIsDisplayed()
            checkArrivalDateHintIsDisplayed()
        }
    }

    @Test
    fun isFormCleared_whenCancelTripIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()

            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)

            clickCancelButton()

            clickAddTripFab()
            clickSingleStopButton()

            inputAddressAndConfirm()
            checkTripNameHintIsDisplayed()
            checkDepartureDateHintIsDisplayed()
            checkArrivalDateHintIsDisplayed()
        }
    }

    @Test
    fun isSaveTripBtnNotEnabled_whenTripNameIsNotProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)
            checkSaveButtonIsNotEnabled()
        }
    }

    @Test
    fun isSaveTripBtnNotEnabled_whenTripNameIsErased() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)
            clearTripName()
            checkSaveButtonIsNotEnabled()
        }
    }

    @Test
    fun isSaveTripBtnNotEnabled_whenArrivalDateNotProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputDepartureDate(2020, 11, 30)
            checkSaveButtonIsNotEnabled()
        }
    }

    @Test
    fun isSaveTripBtnNotEnabled_whenDepartureDateIsNotProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            checkSaveButtonIsNotEnabled()
        }
    }

    @Test
    fun isSaveTripBtnEnabled_whenTripNameArrivalDepartureDateIsProvided() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)
            checkSaveButtonIsEnabled()
        }
    }

    @Test
    fun isCancelTripBtnShown_whenPlaceConfirmIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            checkCancelButtonIsDisplayed()
            checkSaveButtonIsDisplayed()
        }
    }

    @Test
    fun isCancelTripBtnHidden_whenBackIsClicked() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            clickAddressEditBack()
            checkCancelButtonDoesNotExist()
            checkSaveButtonDoesNotExist()
        }
    }

    @Test
    fun isNetworkUnavailableErrorNotShown_whenAddTripFabIsClicked_whileInternetAvailable() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            checkInternetUnavailableErrorDoesNotExist()
        }
    }

    @Test
    fun isAddTripFabDisplayed_afterFirstSingleStopTripIsSaved() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)
            clickSaveButton()
            waitForAddTripFab()
            checkAddTripFabIsDisplayed()
        }
    }

    @Test
    fun isSearchBarDisplayedAndFocused_whenSingleStopBtnIsClicked_afterFirstTripIsSaved() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)
            clickSaveButton()
            waitForAddTripFab()
            clickAddTripFab()
            checkAddTripFabDoesNotExist()
            clickSingleStopButton()
            checkSearchBarIsDisplayed()
            checkSearchBarIsFocused()
        }
    }

    @Test
    fun isFormEmpty_whenStartingAddingSecondTrip_afterFirstTripIsSaved() {
        with(MapScreenRobot(composeTestRule)) {
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            inputTripName()
            inputArrivalDate(2017, 6, 10)
            inputDepartureDate(2020, 11, 30)
            clickSaveButton()
            waitForAddTripFab()
            clickAddTripFab()
            clickSingleStopButton()
            inputAddressAndConfirm()
            checkTripNameHintIsDisplayed()
            checkArrivalDateHintIsDisplayed()
            checkDepartureDateHintIsDisplayed()
        }
    }
}
