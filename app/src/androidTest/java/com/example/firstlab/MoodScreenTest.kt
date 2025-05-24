package com.example.firstlab

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firstlab.common.Constant.ARG_MOOD_DATA
import com.example.firstlab.domain.entity.EmotesCategory
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.domain.entity.MoodCategory
import com.example.firstlab.domain.entity.TimeOfDay
import com.example.firstlab.page.`object`.DailyMoodScreen
import com.example.firstlab.presentation.screen.MoodStatisticFragment
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoodScreenTest : TestCase() {
    @Test
    fun testDailyMoodScreen() {
        val moodList = listOf(
            MoodCategory(Pair(1f, EmotionType.GREEN), TimeOfDay.EARLY_MORNING),
            MoodCategory(Pair(0.5f, EmotionType.YELLOW), TimeOfDay.MORNING),
            MoodCategory(Pair(0.5f, EmotionType.RED), TimeOfDay.MORNING),
            MoodCategory(Pair(1f, EmotionType.RED), TimeOfDay.DAY),
            MoodCategory(Pair(0.3f, EmotionType.BLUE), TimeOfDay.EVENING),
            MoodCategory(Pair(0.7f, EmotionType.RED), TimeOfDay.EVENING)
        )

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putParcelableArrayList(ARG_MOOD_DATA, ArrayList(moodList))
                }
                val scenario =
                    launchFragmentInContainer<MoodStatisticFragment>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                DailyMoodScreen {
                    moodHeader.isDisplayed()
                    columnsContainer.isDisplayed()
                    earlyMorningNumber {
                        isDisplayed()
                        hasText("1")
                    }
                    morningNumber {
                        isDisplayed()
                        hasText("2")
                    }
                    lateEveningNumber {
                        isDisplayed()
                        hasText("0")
                    }
                }
            }

        }
    }
}