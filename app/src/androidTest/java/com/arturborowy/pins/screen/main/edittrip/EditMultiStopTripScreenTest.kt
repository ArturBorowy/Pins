package com.arturborowy.pins.screen.main.edittrip

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.data.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.screen.main.MockSystemAbstractionModule
import com.arturborowy.pins.screen.main.map.MapScreenRobot
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@HiltAndroidTest
class EditMultiStopTripScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun isDataCorrect_onEditingScreen() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
            checkTripNameIsDisplayed(EditTripScreenRobot.MOCK_TRIP_NAME)
            checkFirstStopDataIsDisplayed()
            clickNextStopNavigationButton()
            checkFirstStopDataIsNotDisplayed()
            checkSecondStopDataIsDisplayed()
            clickPreviousStopNavigationButton()
            checkFirstStopDataIsDisplayed()
            checkSecondStopDataIsNotDisplayed()
        }
    }

    @Test
    fun isTripChangedOnList_whenEdited() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
            replaceTripName("newTripName")
            inputDate("10 Jun 2017", 2000, 1, 1)
            clickNextStopNavigationButton()
            inputDate("20 Dec 2020", 2000, 4, 10)
            clickFirstSaveChangesButton()
            checkMultiStopDatesOnTripList("01 Jan 2000", "10 Apr 2000")
        }

        assertIsTripNameOnTripListCorrect("newTripName")
    }

    @Test
    fun isAddressCleared_whenBackIsClicked() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
        }

        with(EditTripScreenRobot(composeTestRule)) {
            checkTripNameFieldDoesNotExist()
            checkArrivalDateFieldDoesNotExist()
            checkDepartureDateFieldDoesNotExist()
            checkDeleteButtonDoesNotExist()
            checkSaveChangesButtonDoesNotExist()
        }

        with(MapScreenRobot(composeTestRule)) {
            checkAddressHintContainsHintText()
            checkAddressEditBackIsDisplayed()
        }
    }

    @Test
    fun arePredictionsShown_whenPlaceNameIsProvided() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
            replaceAddressFromCurrentPlaceName(
                MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName,
                MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING
            )
            waitForPredictions(MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label)
            checkPredictionsAreDisplayed()
        }
    }

    @Test
    fun isPlaceConfirmShown_whenPredictionIsChosen() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
            replaceAddressSearchText(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)
            clickFirstPrediction()
            checkAddressConfirmButtonIsDisplayed()
            checkPredictionsAreNotDisplayed()
        }
    }

    @Test
    fun tripDoesNotAppearOnList_whenDeleted() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
            clickDeleteButton()
            checkTripDatesTagDoesNotExist()
            checkTripNameTagDoesNotExist()
            checkTripPlaceTagDoesNotExist()
        }
    }

    @Test
    fun isTripNameShown_whenPlaceConfirmIsClicked() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
            replaceAddressSearchText(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)
            clickFirstPrediction()
            clickAddressConfirmButton()
        }

        with(EditTripScreenRobot(composeTestRule)) {
            checkTripNameIsDisplayed(EditTripScreenRobot.MOCK_TRIP_NAME)
            checkDeleteButtonIsDisplayed()
            checkSaveChangesButtonIsDisplayed()
        }

        with(MapScreenRobot(composeTestRule)) {
            checkAddressEditBackIsDisplayed()
            checkArrivalDateHintIsDisplayed()
        }
    }

    @Test
    fun isTripNameShown_whenBackIsClicked() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
            replaceAddressSearchText(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)
            clickFirstPrediction()
            clickAddressConfirmButton()
        }

        with(EditTripScreenRobot(composeTestRule)) {
            checkTripNameIsDisplayed(EditTripScreenRobot.MOCK_TRIP_NAME)
            checkDeleteButtonIsDisplayed()
        }

        with(MapScreenRobot(composeTestRule)) {
            checkAddressEditBackIsDisplayed()
            checkArrivalDateHintIsDisplayed()
        }
    }

    @Test
    fun isSaveChangesBtnShown_whenBackIsClickedTwice() {
        addMultiStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
        }

        with(EditTripScreenRobot(composeTestRule)) {
            waitForSaveChangesButtonCount(0)
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
        }

        with(EditTripScreenRobot(composeTestRule)) {
            waitForSaveChangesButtonToAppear()
            checkSaveChangesButtonIsDisplayed()
        }
    }
}
