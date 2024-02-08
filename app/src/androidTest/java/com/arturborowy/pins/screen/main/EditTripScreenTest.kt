package com.arturborowy.pins.screen.main

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.ui.composable.TripViewTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class EditTripScreenTest : BaseComposeTest<MainActivity>() {

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun isTripNameCorrect_onEditingScreen() {
        addTripViaTripListAndGoToEdit()

        composeTestRule.onNodeWithText(MOCK_TRIP_NAME)
            .assertIsDisplayed()
    }

    @Test
    fun isPlaceNameCorrect_onEditingScreen() {
        addTripViaTripListAndGoToEdit()

        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName)
            .assertIsDisplayed()
    }

    @Test
    fun isArrivalDateCorrect_onEditingScreen() {
        addTripViaTripListAndGoToEdit()

        composeTestRule.onNodeWithText("10 Jun 2017")
            .assertIsDisplayed()
    }

    @Test
    fun isDepartureDateCorrect_onEditingScreen() {
        addTripViaTripListAndGoToEdit()

        composeTestRule.onNodeWithText("30 Nov 2020")
            .assertIsDisplayed()
    }

    @Test
    fun isTripNameChangedOnList_whenEdited() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        val newTripName = "newTripName"

        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .performTextReplacement(newTripName)

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_save_changes)
            .performClick()

        assertIsTripNameOnTripListCorrect(newTripName)
    }

    @Test
    fun areDatesChangedOnList_whenEdited() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        inputDate("10 Jun 2017", 2000, 1, 1)
        inputDate("30 Nov 2020", 2000, 4, 10)

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_save_changes)
            .performClick()

        assertAreDatesOnTripListCorrect("01 Jan 2000 - 10 Apr 2000")
    }

    @Test
    fun isAddressCleared_whenBackIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .assertTextContains(getString(R.string.add_trip_hint_name))
    }

    @Test
    fun isBackHidden_whenBackIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .assertDoesNotExist()
    }

    @Test
    fun isTripNameHidden_whenBackIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .assertDoesNotExist()
    }

    @Test
    fun isArrivalDateHidden_whenBackIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date)
            .assertDoesNotExist()
    }

    @Test
    fun isDepartureDateHidden_whenBackIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_departure_date)
            .assertDoesNotExist()
    }

    @Test
    fun isDeleteTripBtnHidden_whenBackIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_delete)
            .assertDoesNotExist()
    }

    @Test
    fun isSaveChangesBtnHidden_whenBackIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_save_changes)
            .assertDoesNotExist()
    }

    @Test
    fun arePredictionsShown_whenPlaceNameIsProvided() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertIsDisplayed()
        }
    }

    @Test
    fun isPlaceConfirmShown_whenPredictionIsChosen() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextReplacement(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm)
            .assertIsDisplayed()
    }

    @Test
    fun arePredictionsHidden_whenPredictionIsChosen() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextReplacement(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm)
            .performClick()

        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertDoesNotExist()
        }
    }

    @Test
    fun isTripNameShown_whenPlaceConfirmIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextReplacement(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm)
            .performClick()

        composeTestRule.onNodeWithText(MOCK_TRIP_NAME)
            .assertIsDisplayed()
    }

    @Test
    fun isBackShown_whenPlaceConfirmIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextReplacement(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .assertIsDisplayed()
    }

    @Test
    fun isDepartureDateShown_whenPlaceConfirmIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
        editPlace()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_departure_date).assertIsDisplayed()
    }

    @Test
    fun isArrivalDateShown_whenPlaceConfirmIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
        editPlace()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date).assertIsDisplayed()
    }

    @Test
    fun tripDiesNotAppearOnList_whenDeleted() {
        addTripViaTripList()

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
    fun isDeleteTripBtnShown_whenPlaceConfirmIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
        editPlace()

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_delete).assertIsDisplayed()
    }

    @Test
    fun isSaveChangesBtnShown_whenPlaceConfirmIsClicked() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
        editPlace()

        composeTestRule.onNodeWithText(R.string.edit_trip_btn_save_changes).assertIsDisplayed()
    }

    private fun addTripViaTripListAndGoToEdit() {
        addTripViaTripList()

        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        chooseTripToEdit()
    }

    private fun chooseTripToEdit() {
        val expectedContentDescription = resourcesRepository.getString(
            R.string.trip_list_cd_edit,
            MOCK_TRIP_NAME
        )

        composeTestRule.waitUntilExactlyOneExists(
            androidx.compose.ui.test.hasContentDescription(expectedContentDescription),
            5000L
        )

        composeTestRule.onNodeWithContentDescription(expectedContentDescription)
            .performClick()
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
