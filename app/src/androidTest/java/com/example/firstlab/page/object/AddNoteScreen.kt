package com.example.firstlab.page.`object`

import com.example.firstlab.R
import com.example.firstlab.presentation.AddNoteScreen
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KButton

object AddNoteScreen : KScreen<com.example.firstlab.page.`object`.AddNoteScreen>() {
    override val layoutId = R.layout.add_note_screen
    override val viewClass: Class<*>? = null

    val chosenCard = KView { withId(R.id.chosenFeelingCard) }
    val backButton = KButton { withId(R.id.back_to_bubbles) }
    val toolBar = KView { withId(R.id.NotesToolbar) }
    val saveButton = KButton { withId(R.id.saveButton) }
    val activitiesChipGroup = KView { withId(R.id.activitiesChipGroup) }
    val companyChipGroup = KView { withId(R.id.companyChipGroup) }
    val placesChipGroup = KView { withId(R.id.placeChipGroup) }
}