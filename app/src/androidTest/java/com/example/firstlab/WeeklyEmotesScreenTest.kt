package com.example.firstlab

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firstlab.common.Constant.ARG_WEEKLY_EMOTES
import com.example.firstlab.domain.entity.Emotion
import com.example.firstlab.domain.entity.WeeklyEmoteItem
import com.example.firstlab.page.`object`.WeeklyEmotesScreen
import com.example.firstlab.presentation.screen.WeeklyEmotesFragment
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeeklyEmotesScreenTest : TestCase() {
    @Test
    fun testWeeklyEmotesWithData() {

        val testWeeklyEmotes = listOf(
            WeeklyEmoteItem(
                "17 фев",
                "Понедельник",
                listOf(
                    Emotion(R.drawable.mithosis_emote, "Спокойствие"),
                    Emotion(R.drawable.lightning_emote, "Продуктивность"),
                    Emotion(R.drawable.ellipse_icon, "Счастье"),
                )
            ),
            WeeklyEmoteItem(
                "18 фев",
                "Вторник",
                listOf(
                    Emotion(R.drawable.shell_icon, "Выгорание"),
                )
            ),
            WeeklyEmoteItem(
                "19 фев",
                "Среда",
                listOf()
            ),
            WeeklyEmoteItem(
                "20 фев",
                "Четверг",
                listOf()
            ),
            WeeklyEmoteItem(
                "21 фев",
                "Пятница",
                listOf()
            ),
            WeeklyEmoteItem(
                "22 фев",
                "Суббота",
                listOf()
            ),
            WeeklyEmoteItem(
                "23 фев",
                "Воскресенье",
                listOf()
            ),
        )

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putParcelableArrayList(ARG_WEEKLY_EMOTES, ArrayList(testWeeklyEmotes))
                }
                val scenario =
                    launchFragmentInContainer<WeeklyEmotesFragment>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                WeeklyEmotesScreen {
                    emotionsCategoryHeader.isDisplayed()
                    weeklyEmotesRecycler {
                        isDisplayed()
                        hasSize(7)
                        childAt<WeeklyEmotesScreen.WeeklyEmoteItemObject>(0) {
                            dayInfo.isDisplayed()

                            date {
                                isDisplayed()
                                hasText("17 фев")
                            }
                            weekDay {
                                isDisplayed()
                                hasText("Понедельник")
                            }
                            weekEmotes {
                                isDisplayed()
                                matches { hasChildCount(3) }
                            }
                            emotesIcons {
                                isDisplayed()
                                matches { hasChildCount(3) }
                            }
                            itemDivider {
                                isDisplayed()
                            }
                        }
                    }
                }
            }
        }

    }
}