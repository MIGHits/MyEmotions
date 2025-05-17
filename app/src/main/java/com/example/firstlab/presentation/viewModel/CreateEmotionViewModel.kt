package com.example.firstlab.presentation.viewModel

import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.firstlab.App
import com.example.firstlab.R
import com.example.firstlab.models.EmotionType
import com.example.firstlab.presentation.state.EmotionDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CreateEmotionViewModel : ViewModel() {
    private val _createState = MutableStateFlow(EmotionDetailsState())
    val createState: StateFlow<EmotionDetailsState> get() = _createState

    fun chooseEmotion(name: String, color: Int) {
        val emotionType = convertColorToEmotion(color)
        _createState.update {
            _createState.value.copy(
                emotionType = emotionType,
                name = name,
                createTime = System.currentTimeMillis(),
                iconRes = convertColorToIcon(emotionType)
            )
        }
    }

    private fun convertColorToEmotion(color: Int): EmotionType? {
        return when (color) {
            (ContextCompat.getColor(App.app, R.color.redGradient)) -> EmotionType.RED
            (ContextCompat.getColor(App.app, R.color.yellowGradient)) -> EmotionType.YELLOW
            (ContextCompat.getColor(App.app, R.color.blueGradient)) -> EmotionType.BLUE
            (ContextCompat.getColor(App.app, R.color.greenGradient)) -> EmotionType.GREEN
            else -> null
        }
    }

    private fun convertColorToIcon(emotionType: EmotionType?): Int? {
        return when (emotionType) {
            EmotionType.RED -> R.drawable.soft_flower_icon
            EmotionType.YELLOW -> R.drawable.lightning_icon
            EmotionType.BLUE -> R.drawable.sadness_icon
            EmotionType.GREEN -> R.drawable.mithosis_icon
            else -> null
        }
    }
}
