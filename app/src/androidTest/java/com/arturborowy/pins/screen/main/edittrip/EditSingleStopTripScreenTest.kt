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
class EditSingleStopTripScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val networkStateRepository: NetworkStateRepository =
        MockSystemAbstractionModule.networkStateRepository

    @BindValue
    @JvmField
    val localeRepository = MockSystemAbstractionModule.localeRepository

    @Test
    fun isTripNameCorrect_onEditingScreen() {
        addSingleStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
            checkTripNameIsDisplayed(EditTripScreenRobot.MOCK_TRIP_NAME)
            checkPlaceNameIsDisplayed(MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName)
            checkDateIsDisplayed("10 Jun 2017")
            checkDateIsDisplayed("30 Nov 2020")
        }
    }

    @Test
    fun isTripChangedOnList_whenEdited() {
        addSingleStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
            waitForDateText("10 Jun 2017")
            replaceTripName("newTripName")
            inputDate("10 Jun 2017", 2000, 1, 1)
            inputDate("30 Nov 2020", 2000, 4, 10)
            clickSaveChangesButton()
        }

        assertIsTripNameOnTripListCorrect("newTripName")
        assertAreDatesOnTripListCorrect("01 Jan 2000 - 10 Apr 2000")
    }

    @Test
    fun isAddressCleared_whenBackIsClicked() {
        addSingleStopTripViaTripList()

        with(EditTripScreenRobot(composeTestRule)) {
            goToTripList()
            chooseTripToEdit()
        }

        with(MapScreenRobot(composeTestRule)) {
            clickAddressEditBack()
        }

        with(EditTripScreenRobot(composeTestRule)) {
            waitForSaveChangesButtonCount(0)
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
        addSingleStopTripViaTripList()

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
        addSingleStopTripViaTripList()

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
        addSingleStopTripViaTripList()

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
        addSingleStopTripViaTripList()

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
            checkDepartureDateHintIsDisplayed()
        }
    }

    @Test
    fun isTripNameShown_whenBackIsClicked() {
        addSingleStopTripViaTripList()

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
            checkDepartureDateHintIsDisplayed()
        }
    }

    @Test
    fun isSaveChangesBtnShown_whenBackIsClickedTwice() {
        addSingleStopTripViaTripList()

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
