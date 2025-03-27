package com.example.firstlab

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firstlab.page.`object`.AddNoteScreen
import com.example.firstlab.page.`object`.ChooseFeelingScreen
import com.example.firstlab.page.`object`.EmotionsCategoryScreen
import com.example.firstlab.page.`object`.MainScreen
import com.example.firstlab.page.`object`.NavigationScreen
import com.example.firstlab.page.`object`.SettingsScreen
import com.example.firstlab.presentation.MainActivity
import com.example.firstlab.presentation.NavigationActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest : TestCase() {
    @Test
    fun navigationTest() {

        run {
            step("EnterApp") {
                ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                    step("Check screen views") {
                        MainScreen {
                            greetings.isDisplayed()
                            gradientView.isDisplayed()
                            entranceButton.isDisplayed()
                            entranceButton.isClickable()
                            entranceButton.click()
                        }
                    }
                }
            }
            step("FeelingsScreen is open") {
                ActivityScenario.launch(NavigationActivity::class.java)
                com.example.firstlab.page.`object`.FeelingsScreen {
                    addButton {
                        isDisplayed()
                        isClickable()
                        click()
                    }
                }
            }
            step("ChooseEmotionFragmentOpen") {
                ChooseFeelingScreen {
                    continueButton.isDisabled()
                    onView(withId(R.id.ballsRecycler)).perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                            0,
                            click()
                        )
                    )
                    infoLayout.isDisplayed()
                    continueButton.isEnabled()
                    continueButton.isClickable()
                    continueButton.click()
                }
            }
            step("AddNoteFragmentOpen") {
                AddNoteScreen {
                    toolBar.isDisplayed()
                    chosenCard.isDisplayed()
                    backButton {
                        isDisplayed()
                        isClickable()
                    }
                    activitiesChipGroup {
                        isDisplayed()
                    }
                    saveButton {
                        isDisplayed()
                        isClickable()
                        click()
                    }
                }
            }
            step("OpenStatistic") {
                NavigationScreen {
                    bottomNavigationView {
                        onView(withId(R.id.statisticScreen)).perform(ViewActions.click())
                    }
                }
                EmotionsCategoryScreen {
                    emotionsCategoryHeader.isDisplayed()
                    noteCounter.isDisplayed()
                    bubbleChart.isDisplayed()
                }
            }
            step("OpenSettings") {
                NavigationScreen {
                    bottomNavigationView {
                        onView(withId(R.id.settingsScreen)).perform(ViewActions.click())
                    }
                }
                SettingsScreen {
                    settingsHeader.isDisplayed()
                    collapsingBar.isDisplayed()
                    addNotificationButton.isDisplayed()
                }
            }
        }
    }
}