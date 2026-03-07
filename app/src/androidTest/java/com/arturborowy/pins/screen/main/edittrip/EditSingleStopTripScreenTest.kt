package com.arturborowy.pins.screen.main.edittrip

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.data.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.data.system.NetworkStateRepository
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.screen.main.BottomNavItem
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.screen.main.MockSystemAbstractionModule
import com.arturborowy.pins.ui.composable.TripViewTag
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test

@UninstallModules(SystemAbstractionModule::class)
@OptIn(ExperimentalTestApi::class)
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
        addSingleStopTripViaTripListAndGoToEdit()

        //isTripNameCorrect_onEditingScreen
        composeTestRule.onNodeWithText(MOCK_TRIP_NAME)
            .assertIsDisplayed()

        //isPlaceNameCorrect_onEditingScreen
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName)
            .assertIsDisplayed()

        //isArrivalDateCorrect_onEditingScreen
        composeTestRule.onNodeWithText("10 Jun 2017")
            .assertIsDisplayed()

        //addTripViaTripListAndGoToEdit
        composeTestRule.onNodeWithText("30 Nov 2020")
            .assertIsDisplayed()
    }

    @Test
    fun isTripChangedOnList_whenEdited() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        val newTripName = "newTripName"

        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .performTextReplacement(newTripName)

        inputDate("10 Jun 2017", 2000, 1, 1)
        inputDate("30 Nov 2020", 2000, 4, 10)

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_save_changes)
            .performClick()

        assertIsTripNameOnTripListCorrect(newTripName)
        assertAreDatesOnTripListCorrect("01 Jan 2000 - 10 Apr 2000")
    }

    @Test
    fun isAddressCleared_whenBackIsClicked() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        //isAddressCleared_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .assertTextContains(getString(R.string.add_trip_hint_name))

        //isBackShown_evenWhenBackIsClicked
        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .assertIsDisplayed()

        //isTripNameHidden_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .assertDoesNotExist()

        //isArrivalDateHidden_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date)
            .assertDoesNotExist()

        //isDepartureDateHidden_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_departure_date)
            .assertDoesNotExist()

        //isDeleteTripBtnHidden_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.edit_trip_btn_delete)
            .assertDoesNotExist()

        //isSaveChangesBtnHidden_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.edit_trip_btn_save_changes)
            .assertDoesNotExist()
    }

    @Test
    fun arePredictionsShown_whenPlaceNameIsProvided() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName)
            .performTextReplacement(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.waitUntilExactlyOneExists(
            hasText(MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label),
            5000L
        )
        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertIsDisplayed()
        }
    }

    @Test
    fun isPlaceConfirmShown_whenPredictionIsChosen() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextReplacement(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        //isPlaceConfirmShown_whenPredictionIsChosen
        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm)
            .assertIsDisplayed()

        //arePredictionsHidden_whenPredictionIsChosen
        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertDoesNotExist()
        }
    }

    @Test
    fun tripDoesNotAppearOnList_whenDeleted() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_delete).performClick()

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_DATES)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(TripViewTag.TRIP_NAME)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(TripViewTag.TRIP_PLACE)
            .assertDoesNotExist()
    }

    @Test
    fun isTripNameShown_whenPlaceConfirmIsClicked() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
        editPlace()

        //isTripNameShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithText(MOCK_TRIP_NAME)
            .assertIsDisplayed()

        //isBackShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .assertIsDisplayed()

        //isDepartureDateShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_departure_date).assertIsDisplayed()

        //isArrivalDateShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date).assertIsDisplayed()

        //isDeleteTripBtnShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithText(R.string.edit_trip_btn_delete).assertIsDisplayed()

        //isSaveChangesBtnShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithText(R.string.edit_trip_btn_save_changes).assertIsDisplayed()
    }

    @Test
    fun isTripNameShown_whenBackIsClicked() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
        editPlace()

        //isTripNameShown_whenBackIsClicked
        composeTestRule.onNodeWithText(MOCK_TRIP_NAME)
            .assertIsDisplayed()

        //isBackShown_whenBackIsClicked
        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .assertIsDisplayed()

        //isDepartureDateShown_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_departure_date).assertIsDisplayed()

        //isArrivalDateShown_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date).assertIsDisplayed()

        //isDeleteTripBtnShown_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.edit_trip_btn_delete).assertIsDisplayed()
    }

    @Test
    fun isSaveChangesBtnShown_whenBackIsClickedTwice() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()
    }

    private fun addSingleStopTripViaTripListAndGoToEdit() {
        addSingleStopTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
    }

    private fun chooseTripToEdit() {
        val expectedContentDescription = resourcesRepository.getString(
            R.string.trip_list_cd_edit,
            MOCK_TRIP_NAME
        )

        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription(expectedContentDescription),
            5000L
        )

        composeTestRule.onNodeWithContentDescription(expectedContentDescription)
            .performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasText(getString(R.string.edit_trip_btn_save_changes)),
            5000L
        )
    }

    private fun editPlace() {
        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextReplacement(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm)
            .performClick()
    }
}
