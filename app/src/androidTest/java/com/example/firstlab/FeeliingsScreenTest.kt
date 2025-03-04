package com.example.firstlab

import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.firstlab.models.FeelingItem
import com.example.firstlab.page.`object`.FeelingItemObject
import com.example.firstlab.page.`object`.FeelingsScreen
import com.example.firstlab.presentation.FeelingsScreen.Companion.ARG_POST_AMOUNT
import com.example.firstlab.presentation.FeelingsScreen.Companion.ARG_STAT_DATA
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeeliingsScreenTest : TestCase() {
    @Test
    fun checkFeelingsScreenWithCustomData() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val posts = 2
        val testFeelings = listOf(
            FeelingItem(
                "вчера,23:40",
                "выгорание",
                R.drawable.sadness_icon,
                R.drawable.blue_type_gradient,
                ContextCompat.getColor(context, R.color.blueGradient)
            ),
            FeelingItem(
                "воскресенье,16:12",
                "продуктивность",
                R.drawable.lightning_icon,
                R.drawable.yellow_type_gradient,
                ContextCompat.getColor(context, R.color.yellowGradient)
            ),
            FeelingItem(
                "воскресенье,03:59",
                "беспокойство",
                R.drawable.soft_flower_icon,
                R.drawable.red_type_gradient,
                ContextCompat.getColor(context, R.color.redGradient)
            )
        )

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putInt(ARG_POST_AMOUNT, posts)
                    putParcelableArrayList(ARG_STAT_DATA, ArrayList(testFeelings))
                }
                val scenario =
                    launchFragmentInContainer<com.example.firstlab.presentation.FeelingsScreen>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                FeelingsScreen {
                    recyclerView {
                        isDisplayed()
                        hasSize(3)
                        childAt<FeelingItemObject>(0) {
                            feelingName.isDisplayed()
                            feelingName.hasText("выгорание")
                            feelingTime.isDisplayed()
                            feelingTime.hasText("вчера,23:40")
                            feelingIcon.isDisplayed()
                            feelingIcon.matches { withDrawable(R.drawable.sadness_icon) }
                        }
                    }
                    notesGoal.isDisplayed()
                    notesGoal.hasText("2 записи")
                    notesStreak.isDisplayed()
                    notesStreak.hasText("2 записи")
                    notesCount.isDisplayed()
                    notesCount.hasText("2 записи")
                }
            }
        }
    }
}
