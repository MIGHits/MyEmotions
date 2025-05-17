package com.example.firstlab

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.firstlab.common.Constant.ARG_ACTIVITIES_DATA
import com.example.firstlab.common.Constant.ARG_COMPANY_DATA
import com.example.firstlab.common.Constant.ARG_PLACES_DATA
import com.example.firstlab.page.`object`.AddNoteScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddNoteScreenTest : TestCase() {
    @Test
    fun testNoteScreen() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val company = listOf(
            "Test 1", "Test 2", "Test 3", "Test 4"
        )
        val places = listOf("Place1", "Place2", "Place3", "Place4")

        val activities = listOf("activity1", "activity2", "activity3", "activity4")

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putStringArrayList(ARG_COMPANY_DATA, ArrayList(company))
                    putStringArrayList(ARG_PLACES_DATA, ArrayList(places))
                    putStringArrayList(ARG_ACTIVITIES_DATA, ArrayList(activities))
                }
                val scenario =
                    launchFragmentInContainer<com.example.firstlab.presentation.screen.AddNoteScreen>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                AddNoteScreen {
                    toolBar.isDisplayed()
                    chosenCard.isDisplayed()
                    backButton.apply {
                        isDisplayed()
                        isClickable()
                    }
                    saveButton.apply {
                        isDisplayed()
                        isClickable()
                    }
                    activitiesChipGroup.apply {
                        isDisplayed()
                    }

                    onView(
                        allOf(
                            isDescendantOfA(withId(R.id.activitiesChipGroup)), withText("activity1")
                        )
                    ).check(matches(isDisplayed()))

                    onView(
                        allOf(
                            isDescendantOfA(withId(R.id.placeChipGroup)), withText("Place1")
                        )
                    ).check(matches(isDisplayed()))

                    onView(
                        allOf(
                            isDescendantOfA(withId(R.id.companyChipGroup)), withText("Test 1")
                        )
                    ).check(matches(isDisplayed()))

                    onView(
                        allOf(
                            isDescendantOfA(withId(R.id.activitiesChipGroup)),
                            withText("activity1")
                        )
                    ).perform(click())

                    onView(
                        allOf(
                            isDescendantOfA(withId(R.id.activitiesChipGroup)),
                            withText("activity1")
                        )
                    ).check(matches(isChecked()))
                }
            }
        }
    }
}
