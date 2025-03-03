package com.example.firstlab

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.firstlab.page.`object`.MainScreen
import com.example.firstlab.presentation.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenHappyPathTest : TestCase() {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun testMainScreenDisplay() {
        run {
            step("Open app") {
                ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                    step("Check screen views") {
                        MainScreen {
                            greetings.isDisplayed()
                            gradientView.isDisplayed()
                            entranceButton.isDisplayed()
                            entranceButton.isClickable()
                        }
                    }
                }
            }
        }
    }
}