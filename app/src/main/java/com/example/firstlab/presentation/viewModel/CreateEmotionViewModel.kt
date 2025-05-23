package com.example.firstlab.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstlab.domain.usecase.CreateEmotionUseCase
import com.example.firstlab.domain.usecase.GetEmotionByIdUseCase
import com.example.firstlab.presentation.mapper.EmotionsMapper
import com.example.firstlab.extension.convertColorToEmotion
import com.example.firstlab.extension.convertColorToIcon
import com.example.firstlab.extension.convertTime
import com.example.firstlab.extension.toDomain
import com.example.firstlab.presentation.models.EmotionFullModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateEmotionViewModel(
    private val createEmotionUseCase: CreateEmotionUseCase,
    private val getEmotionByIdUseCase: GetEmotionByIdUseCase,
    private val emotionsMapper: EmotionsMapper
) : ViewModel() {
    private val _createState = MutableStateFlow(EmotionFullModel())
    val createState: StateFlow<EmotionFullModel> get() = _createState

    fun chooseEmotion(name: String, color: Int) {
        val emotionType = color.convertColorToEmotion()
        _createState.update {
            _createState.value.copy(
                type = emotionType,
                name = name,
                createTime = System.currentTimeMillis().convertTime(),
                iconRes = emotionType?.convertColorToIcon()
            )
        }
    }

    fun getEmotionDetails(id: Int) {
        viewModelScope.launch {
            _createState.update {
                emotionsMapper.mapToFullEmotionModel(getEmotionByIdUseCase(id))
            }
        }
    }

    private fun chooseNotes(actions: List<String>, company: List<String>, places: List<String>) {
        _createState.update {
            _createState.value.copy(
                actions = actions.toSet(),
                company = company.toSet(),
                location = places.toSet()
            )
        }
    }

    fun addEmotion(actions: List<String>, company: List<String>, places: List<String>) {
        chooseNotes(actions, company, places)
        viewModelScope.launch(Dispatchers.IO) {
            createEmotionUseCase(_createState.value.toDomain())
        }
    }
}