package com.example.firstlab.page.`object`

import android.view.View
import com.example.firstlab.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher


object FeelingsScreen : Screen<FeelingsScreen>() {

    val addButton = KButton { withId(R.id.addButton) }
    val recyclerView = KRecyclerView(
        builder = { withId(R.id.recyclerView) },
        itemTypeBuilder = {
            itemType(::FeelingItemObject)
        }
    )
    val notesCount = KTextView { withId(R.id.notesCount) }
    val notesStreak = KTextView { withId(R.id.notesStreak) }
    val notesGoal = KTextView { withId(R.id.notesGoal) }
}

class FeelingItemObject(parent: Matcher<View>) : KRecyclerItem<FeelingItemObject>(parent) {
    val feelingName = KTextView(parent) { withId(R.id.emotion) }
    val feelingTime = KTextView(parent) { withId(R.id.time) }
    val feelingCard = KView(parent) { withId(R.id.feelingCard) }
    val feelingIcon = KView(parent) { withId(R.id.feelingIcon) }
}