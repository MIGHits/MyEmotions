package com.example.firstlab

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firstlab.common.Constant.ARG_NOTIFICATION_DATA
import com.example.firstlab.page.`object`.SettingsScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest : TestCase() {
    @Test
    fun testSettingsScreen() {
        val notification = mutableListOf(
            "20:00",
            "16:00",
            "20:00",
            "16:00",
        )

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putStringArrayList(ARG_NOTIFICATION_DATA, ArrayList(notification))
                }
                val scenario =
                    launchFragmentInContainer<com.example.firstlab.presentation.SettingsScreen>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                SettingsScreen {
                    collapsingBar.isDisplayed()

                    settingsHeader.isDisplayed()
                    notificationSwitcher.isDisplayed()

                    notificationSwitcher {
                        isDisplayed()
                        isNotChecked()
                        click()
                        isChecked()
                    }

                    fingerprintSwitcher {
                        isDisplayed()
                        isNotChecked()
                        click()
                        isChecked()
                    }

                    notificationRecycler {
                        isDisplayed()
                        hasSize(4)
                        childAt<SettingsScreen.NotificationItemObject>(0) {
                            chosenTime {
                                isDisplayed()
                                hasText("20:00")
                            }
                            deleteButton {
                                isDisplayed()
                                isClickable()
                                click()
                            }
                        }
                        hasSize(3)
                    }
                    addNotificationButton {
                        isDisplayed()
                        isClickable()
                        click()
                    }
                }
            }
        }
    }
}