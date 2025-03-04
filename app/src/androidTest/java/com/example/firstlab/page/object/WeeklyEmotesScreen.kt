package com.example.firstlab.page.`object`

import android.view.View
import com.example.firstlab.R
import com.example.firstlab.models.WeeklyEmoteItem
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object WeeklyEmotesScreen : KScreen<WeeklyEmotesScreen>() {
    override val layoutId = R.layout.weekly_emotions
    override val viewClass: Class<*>? = null

    val emotionsCategoryHeader = KTextView() { withId(R.id.emotionsCategoryHeader) }
    val weeklyEmotesRecycler =
        KRecyclerView(
            builder = { withId(R.id.weeklyEmotesRecycler) },
            itemTypeBuilder = { itemType(::WeeklyEmoteItemObject) }
        )

    class WeeklyEmoteItemObject(parent: Matcher<View>) : KRecyclerItem<WeeklyEmoteItem>(parent) {
        val dayInfo = KView(parent) { withId(R.id.dayInfo) }
        val weekDay = KTextView(parent) { withId(R.id.weekDay) }
        val date = KTextView(parent) { withId(R.id.date) }
        val weekEmotes = KView(parent) { withId(R.id.weekEmotes) }
        val emotesIcons = KView(parent) { withId(R.id.emotesIcons) }
        val itemDivider = KView(parent) { withId(R.id.item_divider) }
    }
}