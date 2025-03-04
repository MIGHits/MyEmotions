package com.example.firstlab

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.firstlab.models.BallsItem
import com.example.firstlab.models.EmotesCategory
import com.example.firstlab.models.EmotionType
import com.example.firstlab.page.`object`.ChooseFeelingScreen
import com.example.firstlab.page.`object`.EmotionsCategoryScreen
import com.example.firstlab.presentation.ChooseEmotionScreen.Companion.ARG_BALLS_DATA
import com.example.firstlab.presentation.EmotionsCategoryFragment.Companion.ARG_EMOTES_CATEGORY
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmotionsCategoryScreenTest : TestCase() {
    @Test
    fun testScreenWithData() {
        val categoryList = listOf(
            EmotesCategory(Pair(30f, EmotionType.GREEN)),
            EmotesCategory(Pair(40f, EmotionType.YELLOW)),
            EmotesCategory(Pair(25f, EmotionType.RED)),
            EmotesCategory(Pair(5f, EmotionType.BLUE)),
        )

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putParcelableArrayList(ARG_EMOTES_CATEGORY, ArrayList(categoryList))
                }
                val scenario =
                    launchFragmentInContainer<com.example.firstlab.presentation.EmotionsCategoryFragment>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                EmotionsCategoryScreen {
                    emotionsCategoryHeader.isDisplayed()
                    noteCounter.isDisplayed()
                    bubbleChart.isDisplayed()
                }
            }

        }
    }
}