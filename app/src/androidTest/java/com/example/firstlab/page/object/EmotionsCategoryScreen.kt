package com.example.firstlab.page.`object`

import com.example.firstlab.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KTextView

object EmotionsCategoryScreen : KScreen<EmotionsCategoryScreen>() {
    override val layoutId: Int = R.layout.emotions_category
    override val viewClass: Class<*>? = null

    val emotionsCategoryHeader = KTextView { withId(R.id.emotionsCategoryHeader) }
    val noteCounter = KTextView { withId(R.id.noteCounter) }
    val bubbleChart = KView { withId(R.id.bubbleChart) }
}