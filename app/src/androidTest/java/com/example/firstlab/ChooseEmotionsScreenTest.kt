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
import com.example.firstlab.page.`object`.ChooseFeelingScreen
import com.example.firstlab.presentation.ChooseEmotionScreen.Companion.ARG_BALLS_DATA
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChooseEmotionsScreenTest : TestCase() {
    @Test
    fun checkChooseEmotionsScreenWithCustomData() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val testBalls = listOf(
            BallsItem(ContextCompat.getColor(context, R.color.redGradient), "Ярость"),
            BallsItem(ContextCompat.getColor(context, R.color.redGradient), "Напряжение"),
            BallsItem(
                ContextCompat.getColor(context, R.color.yellowGradient),
                "Возбуждение"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.yellowGradient)),
                "Восторг"
            ),
            BallsItem(ContextCompat.getColor(context, R.color.redGradient), "Зависть"),
            BallsItem(
                ContextCompat.getColor(context, R.color.redGradient),
                "Беспокойство"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.yellowGradient)),
                "Уверенность"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.yellowGradient)),
                "Счастье"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.blueGradient)),
                "Выгопание"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.blueGradient)),
                "Усталость"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.greenGradient)),
                "Спокойствие"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.greenGradient)),
                "Удовлетворенность"
            ),
            BallsItem(
                (ContextCompat.getColor(context, R.color.blueGradient)),
                "Депрессия"
            ),
            BallsItem((ContextCompat.getColor(context, R.color.blueGradient)), "Апатия"),
            BallsItem(
                (ContextCompat.getColor(context, R.color.greenGradient)),
                "Благодарность"
            ),
        )

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putParcelableArrayList(ARG_BALLS_DATA, ArrayList(testBalls))
                }
                val scenario =
                    launchFragmentInContainer<com.example.firstlab.presentation.ChooseEmotionScreen>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                ChooseFeelingScreen {
                    continueButton.isDisabled()
                    onView(withId(R.id.ballsRecycler)).perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                            0,
                            click()
                        )
                    )
                    continueButton.isEnabled()
                    continueButton.isClickable()
                    infoLayout.isDisplayed()
                }
            }

        }
    }
}