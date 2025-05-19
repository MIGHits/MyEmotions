package com.example.firstlab.page.`object`

import android.view.View
import com.example.firstlab.R
import com.example.firstlab.presentation.models.WeeklyEmoteItem
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.switch.KSwitch
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object SettingsScreen : KScreen<SettingsScreen>() {
    override val layoutId = R.layout.settings_screen
    override val viewClass: Class<*>? = null

    val settingsHeader = KTextView { withId(R.id.settingsHeader) }
    val collapsingBar = KView { withId(R.id.collapsingBar) }
    val notificationSwitcher = KSwitch { withId(R.id.notification_switcher) }
    val fingerprintSwitcher = KSwitch { withId(R.id.fingerprint_switcher) }
    val addNotificationButton = KButton { withId(R.id.add_notification) }
    val notificationRecycler =
        KRecyclerView(
            builder = { withId(R.id.notificationRecycler) },
            itemTypeBuilder = { itemType(::NotificationItemObject) }
        )

    class NotificationItemObject(parent: Matcher<View>) :
        KRecyclerItem<NotificationItemObject>(parent) {
        val chosenTime = KTextView(parent) { withId(R.id.chosenTime) }
        val deleteButton = KButton(parent) { withId(R.id.deleteButton) }
    }
}