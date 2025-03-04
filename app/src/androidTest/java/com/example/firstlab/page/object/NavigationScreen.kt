package com.example.firstlab.page.`object`

import com.example.firstlab.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView

object NavigationScreen : KScreen<NavigationScreen>() {
    override val layoutId = R.layout.navigation_activity
    override val viewClass: Class<*>? = null

    val bottomNavigationView = KBottomNavigationView { withId(R.id.bottom_navigation) }
}