package com.example.firstlab

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firstlab.common.Constant.ARG_EMOTES_CATEGORY
import com.example.firstlab.presentation.models.EmotesCategory
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.page.`object`.EmotionsCategoryScreen
import com.example.firstlab.presentation.screen.EmotionsCategoryFragment
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
                    launchFragmentInContainer<EmotionsCategoryFragment>(
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