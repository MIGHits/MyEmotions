package com.example.firstlab.page.`object`

import android.view.View
import com.example.firstlab.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object MostFrequentEmotesScreen : KScreen<MostFrequentEmotesScreen>() {
    override val layoutId = R.layout.most_frequent_emotes
    override val viewClass: Class<*>? = null

    val emotionsFrequentHeader = KTextView { withId(R.id.mostFrequentHeader) }
    val frequentEmotesRecycler = KRecyclerView(
        builder = { withId(R.id.frequentEmotesRecycler) },
        itemTypeBuilder = { itemType(::FrequentEmoteItemObject) }
    )

    class FrequentEmoteItemObject(parent: Matcher<View>) :
        KRecyclerItem<FrequentEmoteItemObject>(parent) {
        val emoteIcon = KView(parent) { withId(R.id.emote_icon) }
        val emoteName = KTextView(parent) { withId(R.id.emoteName) }
        val emoteStripe = KView(parent) { withId(R.id.emoteStripe) }
    }
}