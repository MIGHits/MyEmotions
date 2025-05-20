package com.example.firstlab.presentation.mapper

import com.example.firstlab.R
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.presentation.models.EmotionFullModel
import com.example.firstlab.presentation.models.JournalItem

class EmotionsMapper {
    private fun mapToJournalItem(emotion: EmotionEntity): JournalItem {
        return JournalItem(
            id = emotion.id ?: 0,
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

    fun mapToFullEmotionModel(emotion: EmotionEntity): EmotionFullModel {
        return EmotionFullModel(
            id = emotion.id,
            name = emotion.name,
            type = emotion.type,
            userId = emotion.userId,
            iconRes = emotion.iconRes,
            actions = emotion.actions,
            company = emotion.company,
            location = emotion.location,
            createTime = emotion.createTime?.convertTime()
        )
    }
}