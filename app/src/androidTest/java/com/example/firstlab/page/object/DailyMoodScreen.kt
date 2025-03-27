package com.example.firstlab.page.`object`

import com.example.firstlab.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KTextView

object DailyMoodScreen : KScreen<DailyMoodScreen>() {
    override val layoutId = R.layout.mood_screen_fragment
    override val viewClass: Class<*>? = null

    val moodHeader = KTextView { withId(R.id.moodHeader) }
    val columnsContainer = KView { withId(R.id.columnsContainer) }
    val earlyMorningNumber = KTextView { withId(R.id.early_morning_number) }
    val morningNumber = KTextView { withId(R.id.morning_number) }
    val dayNumber = KTextView { withId(R.id.day_number) }
    val eveningNumber = KTextView { withId(R.id.evening_number) }
    val lateEveningNumber = KTextView { withId(R.id.late_evening_number) }
}