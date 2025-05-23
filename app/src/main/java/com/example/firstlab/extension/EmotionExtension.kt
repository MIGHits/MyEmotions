package com.example.firstlab.extension

import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.example.firstlab.App
import com.example.firstlab.R
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.presentation.models.EmotionFullModel


fun Int.convertColorToEmotion(): EmotionType? {
    return when (this) {
        (ContextCompat.getColor(App.app, R.color.redGradient)) -> EmotionType.RED
        (ContextCompat.getColor(App.app, R.color.yellowGradient)) -> EmotionType.YELLOW
        (ContextCompat.getColor(App.app, R.color.blueGradient)) -> EmotionType.BLUE
        (ContextCompat.getColor(App.app, R.color.greenGradient)) -> EmotionType.GREEN
        else -> null
    }
}

fun EmotionType.convertTypeToColor(): Int {
    return when (this) {
        EmotionType.RED -> (ContextCompat.getColor(App.app, R.color.redGradient))
        EmotionType.GREEN -> (ContextCompat.getColor(App.app, R.color.greenGradient))
        EmotionType.BLUE -> (ContextCompat.getColor(App.app, R.color.blueGradient))
        EmotionType.YELLOW -> (ContextCompat.getColor(App.app, R.color.yellowGradient))
    }
}

fun EmotionType.convertColorToIcon(): Int {
    return when (this) {
        EmotionType.RED -> R.drawable.soft_flower_icon
        EmotionType.YELLOW -> R.drawable.lightning_icon
        EmotionType.BLUE -> R.drawable.sadness_icon
        EmotionType.GREEN -> R.drawable.mithosis_icon
    }
}

fun EmotionFullModel.toDomain(): EmotionEntity {
    return EmotionEntity(
        id = this.id,
        name = this.name,
        userId = this.userId,
        createTime = this.createTime?.parseTimeToMillis()
            ?: System.currentTimeMillis(),
        type = this.type,
        iconRes = this.iconRes,
        actions = this.actions.toList(),
        location = this.location.toList(),
        company = this.company.toList()
    )
}

fun Int.dpToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    )
}
