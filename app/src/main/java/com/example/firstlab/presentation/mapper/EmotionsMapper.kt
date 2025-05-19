package com.example.firstlab.presentation.mapper

import com.example.firstlab.R
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.presentation.models.JournalItem

class EmotionsMapper {
    fun mapToJournalItem(emotion: EmotionEntity): JournalItem {
        return JournalItem(
            time = emotion.createTime?.convertTime() ?: "",
            name = emotion.name,
            icon = emotion.iconRes ?: 0,
            background = when (emotion.type) {
                EmotionType.RED -> {
                    R.drawable.red_type_gradient
                }

                EmotionType.GREEN -> {
                    R.drawable.green_type_gradient
                }

                EmotionType.BLUE -> {
                    R.drawable.blue_type_gradient
                }

                EmotionType.YELLOW -> {
                    R.drawable.yellow_type_gradient
                }

                null -> {
                    R.drawable.app_icon_background_background
                }
            },
            color = emotion.type?.convertTypeToColor() ?: R.color.circleSecondaryColor
        )
    }

    fun mapToJournalItem(emotions: List<EmotionEntity>): List<JournalItem> {
        return emotions.map { mapToJournalItem(it) }
    }
}