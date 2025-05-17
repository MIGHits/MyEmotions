package com.example.firstlab

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firstlab.page.`object`.MainScreen
import com.example.firstlab.presentation.screen.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest : TestCase() {

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