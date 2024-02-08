package com.arturborowy.pins

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.arturborowy.pins.model.db.AppDatabase
import com.arturborowy.pins.model.remote.geocoding.MockGeocodingRepository
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.model.system.ResourcesRepository
import com.arturborowy.pins.screen.main.BottomNavItem
import com.arturborowy.pins.ui.composable.TripViewTag
import com.ultimatelogger.android.output.ALogInitializer
import com.ultimatelogger.multiplatform.tag.TagSettings
import dagger.hilt.android.testing.HiltAndroidRule
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@OptIn(ExperimentalTestApi::class)
abstract class BaseComposeTest<ActivityT : ComponentActivity> {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    abstract val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ActivityT>, ActivityT>

    protected val resourcesRepository by lazy { ResourcesRepository(composeTestRule.activity) }

    @Inject
    lateinit var appDatabase: AppDatabase

    @Before
    open fun init() {
        initLogger()
        hiltRule.inject()
    }

    private fun initLogger() {
        val shouldLog = BuildConfig.DEBUG
        val defaultTagSettings = TagSettings(
            shouldLogFileNameAndLineNum = true,
            shouldLogClassName = true,
            shouldLogMethodName = true
        )

        ALogInitializer.init(shouldLog, defaultTagSettings)
    }

    @After
    open fun tearDown() {
        appDatabase.clearAllTables()
    }

    protected fun getString(@StringRes stringResId: Int) =
        composeTestRule.activity.getString(stringResId)

    protected fun <
            ActivityT : ComponentActivity,
            ActivityScenarioRuleT : ActivityScenarioRule<ActivityT>
            >
            AndroidComposeTestRule<ActivityScenarioRuleT, ActivityT>.onNodeWithText(
        @StringRes textResId: Int
    ) =
        onNodeWithText(getString(textResId))

    protected fun SemanticsNodeInteraction.assertTextContains(@StringRes textResId: Int) =
        assertTextContains(getString(textResId))

    protected fun <
            ActivityT : ComponentActivity,
            ActivityScenarioRuleT : ActivityScenarioRule<ActivityT>
            >
            AndroidComposeTestRule<ActivityScenarioRuleT, ActivityT>.onNodeWithContentDescription(
        @StringRes textResId: Int
    ) =
        onNodeWithContentDescription(getString(textResId))

    protected fun inputTripDetails(confirm: Boolean = true) {
        composeTestRule.onNodeWithText(R.string.add_trip_hint_trip_name)
            .performTextInput(MOCK_TRIP_NAME)

        composeTestRule.onNodeWithText(R.string.add_trip_hint_arrival_date).performClick()

        inputDate(R.string.add_trip_hint_arrival_date, 2017, 6, 10)
        inputDate(R.string.add_trip_hint_departure_date, 2020, 11, 30)

        if (confirm) {
            composeTestRule.onNodeWithText(R.string.create_trip_btn_confirm).performClick()
        }
    }

    protected fun inputDate(dateBtnText: Int, year: Int, month: Int, day: Int) {
        inputDate(getString(dateBtnText), year, month, day)
    }

    protected fun inputDate(dateBtnText: String, year: Int, month: Int, day: Int) {
        composeTestRule.onNodeWithText(dateBtnText).performClick()

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.qualifiedName)))
            .perform(PickerActions.setDate(year, month, day))
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
    }

    protected fun goToTripDetailsInput() {
        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()

        inputTripPlace()
    }

    protected fun inputTripPlace(
        tripPlaceName: String = MockPlacesPredictionRepository.EXPECTED_ADDRESS_PREDICTION_STRING
    ) {
        composeTestRule.onNodeWithText(R.string.add_trip_hint_name)
            .performTextInput(tripPlaceName)
        composeTestRule.onNodeWithText(MockPlacesPredictionRepository.FETCHED_ADDRESS_PREDICTIONS[0].label)
            .performClick()

        composeTestRule.onNodeWithContentDescription(R.string.add_trip_btn_confirm).performClick()
    }

    protected fun goToTripAddingViaTripListScreen() {
        composeTestRule.onNodeWithContentDescription(BottomNavItem.PIN_LIST.name).performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription(R.string.main_bottom_nav_label_add),
            5000L
        )

        composeTestRule.onNodeWithContentDescription(R.string.main_bottom_nav_label_add)
            .performClick()
    }

    protected fun assertAreDatesOnTripListCorrect(dateRangeString: String = "10 Jun 2017 - 30 Nov 2020") {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_DATES)
            .assertTextContains(dateRangeString)
    }

    protected fun assertIsTripNameOnTripListCorrect(
        tripName: String = MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName
    ) {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_NAME)
            .assertTextContains(tripName)
    }

    protected fun assertIsPlaceNameOnTripListCorrect(
        placeName: String = MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.locationName
    ) {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithTag(TripViewTag.TRIP_PLACE)
            .assertTextContains(placeName)
    }

    protected fun assertIsFlagOnTripListCorrect() {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TripViewTag.TRIP_DATES), 5000L)

        composeTestRule.onNodeWithContentDescription(MockGeocodingRepository.GEOCODED_COUNTRY.label)
            .assertIsDisplayed()
    }

    protected fun addTripViaTripList() {
        goToTripAddingViaTripListScreen()
        inputTripPlace()
        inputTripDetails()
    }

    protected fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation()
            .targetContext
            .getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    protected fun hasText(@StringRes resId: Int): SemanticsMatcher =
        hasText(getString(resId))

    protected fun hasContentDescription(@StringRes resId: Int): SemanticsMatcher =
        hasContentDescription(getString(resId))

    companion object {
        const val MOCK_TRIP_NAME = "trip"
    }
}