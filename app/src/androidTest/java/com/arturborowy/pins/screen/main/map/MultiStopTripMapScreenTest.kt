package com.arturborowy.pins.screen.main.map

import android.widget.DatePicker
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import com.arturborowy.pins.BaseComposeTest
import com.arturborowy.pins.R
import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.model.system.NetworkStateRepository
import com.arturborowy.pins.screen.main.MainActivity
import com.arturborowy.pins.screen.main.MockSystemAbstractionModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers
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
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop).assertIsDisplayed()
    }

    @Test
    fun isAddTripFabHidden_whenAddTripFabIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        //isAddTripFabHidden_whenAddTripFabIsClicked
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertDoesNotExist()

        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop)
            .performClick()

        //isSearchBarShown_whenAddSingleStopTripIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_name).assertIsDisplayed()

        //isKeyboardShown_whenAddTripFabIsClicked
        composeTestRule.waitUntil(5000L) {
            isKeyboardShown()
        }
    }

    @Test
    fun isSearchBarHidden_whenAddressEditBackIsClicked() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        //isSearchBarHidden_whenAddressEditBackIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_name).assertDoesNotExist()

        //isAddTripFabShown_whenAddressEditBackIsClicked
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .assertIsDisplayed()
    }

    @Test
    fun arePredictionsShown_whenTextIsProvided() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertExists()
        }
    }

    @Test
    fun arePredictionsHidden_whenPredictionIsChosen() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        //arePredictionsHidden_whenPredictionIsChosen
        MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS.forEach {
            composeTestRule.onNodeWithText(it.label)
                .assertDoesNotExist()
        }

        //isConfirmBtnShown_whenPredictionIsChosen
        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm)
            .assertIsDisplayed()
    }

    @Test
    fun isTripNameCleared_whenAddressEditBackIsClickedTwice() {
        goToMultiStopTripDetailsInput()
        inputMultiStopTripDetails(confirm = false)

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm).performClick()

        //isTripNameCleared_whenAddressEditBackIsClickedTwice
        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .assertIsDisplayed()

        //isArrivalDateCleared_whenAddressEditBackIsClickedTwice
        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date)
            .assertIsDisplayed()
    }

    @Test
    fun isTripNameCleared_whenCancelTripIsClicked() {
        goToMultiStopTripDetailsInput()
        inputMultiStopTripDetails(confirm = false)

        composeTestRule.onNodeWithText(R.string.create_trip_btn_cancel)
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextInput(MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING)

        composeTestRule.onNodeWithText(
            MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label
        ).performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm).performClick()

        //isTripNameCleared_whenCancelTripIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .assertIsDisplayed()

        //isArrivalDateCleared_whenCancelTripIsClicked
        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date)
            .assertIsDisplayed()
    }

    @Test
    fun isSaveTripBtnDisabled_whenTripNameIsNotProvided() {
        goToMultiStopTripDetailsInput()

        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2017, 6, 10))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsNotEnabled()
        composeTestRule.onNodeWithText(R.string.create_trip_btn_next_stop).assertIsNotEnabled()
    }

    @Test
    fun isSaveTripBtnDisabled_whenTripNameIsErased() {
        goToMultiStopTripDetailsInput()
        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2017, 6, 10))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .performTextReplacement("")

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsNotEnabled()
        composeTestRule.onNodeWithText(R.string.create_trip_btn_next_stop).assertIsNotEnabled()
    }

    @Test
    fun isSaveTripBtnDisabled_whenArrivalDateNotProvided() {
        goToMultiStopTripDetailsInput()
        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsNotEnabled()
        composeTestRule.onNodeWithText(R.string.create_trip_btn_next_stop).assertIsNotEnabled()
    }

    @Test
    fun isSaveTripBtnEnabled_whenTripNameArrivalDateIsProvided() {
        goToMultiStopTripDetailsInput()
        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(2017, 6, 10))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsEnabled()
        composeTestRule.onNodeWithText(R.string.create_trip_btn_next_stop).assertIsEnabled()
    }

    @Test
    fun isCancelTripBtnShown_whenPlaceConfirmIsClicked() {
        goToMultiStopTripDetailsInput()

        //isCancelTripBtnShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithText(R.string.create_trip_btn_cancel).assertIsDisplayed()

        //isSaveTripBtnShown_whenPlaceConfirmIsClicked
        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertIsDisplayed()
        composeTestRule.onNodeWithText(R.string.create_trip_btn_next_stop).assertIsDisplayed()
    }

    @Test
    fun isCancelTripBtnHidden_whenBackIsClicked() {
        goToMultiStopTripDetailsInput()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_cd_address_editing_back)
            .performClick()

        //isCancelTripBtnHidden_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.create_trip_btn_cancel).assertDoesNotExist()

        //isSaveTripBtnHidden_whenBackIsClicked
        composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).assertDoesNotExist()
        composeTestRule.onNodeWithText(R.string.create_trip_btn_next_stop).assertDoesNotExist()
    }

    @Test
    fun isNetworkUnavailableErrorNotShown_whenAddTripFabIsClicked_whileInternetAvailable() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
        composeTestRule.onNodeWithText(R.string.add_trip_btn_multi_stop)
            .performClick()

        composeTestRule.onNodeWithText(R.string.add_trip_error_internet_unavailable)
            .assertDoesNotExist()
    }
}
