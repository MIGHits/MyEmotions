package com.example.firstlab

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firstlab.common.Constant.ARG_FREQUENT_DATA
import com.example.firstlab.domain.entity.Emotion
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.page.`object`.MostFrequentEmotesScreen
import com.example.firstlab.presentation.screen.MostFrequentEmotesFragment
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MostFrequentEmotesScreen : TestCase() {
    @Test
    fun testFrequentEmotesWithData() {
        val emotesFrequent = listOf(
            Emotion(R.drawable.mithosis_emote, "Спокойствие", 4, EmotionType.GREEN),
            Emotion(R.drawable.lightning_emote, "Продуктивность", 2, EmotionType.YELLOW),
            Emotion(R.drawable.ellipse_icon, "Счастье", 3, EmotionType.YELLOW),
            Emotion(R.drawable.shell_icon, "Усталость", 1, EmotionType.BLUE)
        )

        run {
            step("open fragment") {
                val args = Bundle().apply {
                    putParcelableArrayList(ARG_FREQUENT_DATA, ArrayList(emotesFrequent))
                }
                val scenario =
                    launchFragmentInContainer<MostFrequentEmotesFragment>(
                        args,
                        themeResId = R.style.Theme_FirstLab
                    )
                scenario.moveToState(Lifecycle.State.RESUMED)
            }

            step("data visibility") {
                MostFrequentEmotesScreen {
                    emotionsFrequentHeader.isDisplayed()
                    frequentEmotesRecycler {
                        isDisplayed()
                        hasSize(4)
                        childAt<MostFrequentEmotesScreen.FrequentEmoteItemObject>(0) {
                            emoteName {
                                isDisplayed()
                                hasText("Спокойствие")
                            }
                            emoteIcon {
                                isDisplayed()
                                matches { withDrawable(R.drawable.mithosis_emote) }
                            }
                            emoteStripe {
                                isDisplayed()
                            }
                        }
                    }
                }
            }
        }
    }
}