package com.example.firstlab.page.`object`

import android.view.View
import com.example.firstlab.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object ChooseFeelingScreen : KScreen<ChooseFeelingScreen>() {
    override val layoutId = R.layout.emotions_choose
    override val viewClass: Class<*>? = null

    val zoomLayout = KView { withId(R.id.zoomLayout) }
    val backButton = KButton { withId(R.id.back_to_feelings) }
    val infoLayout = KView { withId(R.id.infoLayout) }
    val continueButton = KButton { withId(R.id.continue_button) }
    val recyclerView = KRecyclerView(
        builder = { withId(R.id.ballsRecycler) },
        itemTypeBuilder = {
            itemType(::BallsItemObject)
        }
    )

    class BallsItemObject(parent: Matcher<View>) : KRecyclerItem<BallsItemObject>(parent) {
        val ballText = KTextView(parent) { withId(R.id.emotionBall) }
        val сontainer = KView(parent) { withId(R.id.itemContainer) }
    }
}