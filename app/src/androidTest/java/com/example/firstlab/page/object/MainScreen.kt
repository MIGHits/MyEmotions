package com.example.firstlab.page.`object`

import com.example.firstlab.R
import com.example.firstlab.presentation.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object MainScreen : KScreen<MainScreen>() {
    override val layoutId = R.layout.activity_main
    override val viewClass: Class<*>? = null

    val greetings = KTextView {
        withId(R.id.greeting)
    }.apply { isVisible() }

    val gradientView = KView {
        withId(R.id.gradientView)
    }.apply { isVisible() }

    val entranceButton = KButton {
        withId(R.id.entranceButton)
    }.apply {
        isVisible()
        isClickable()
    }
}