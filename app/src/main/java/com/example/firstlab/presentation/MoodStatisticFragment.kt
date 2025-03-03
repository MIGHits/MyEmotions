package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.databinding.MoodScreenFragmentBinding
import com.example.firstlab.databinding.MostFrequentEmotesBinding
import com.example.firstlab.models.Emotion
import com.example.firstlab.models.EmotionType

class MoodStatisticFragment : Fragment(R.layout.mood_screen_fragment) {
    private lateinit var binding: MoodScreenFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoodScreenFragmentBinding.bind(view)

        val earlyMorningList = listOf(Pair(EmotionType.GREEN, 1f))
        val morningList = listOf(Pair(EmotionType.YELLOW, 0.5f), Pair(EmotionType.RED, 0.5f))
        val dayList = listOf(Pair(EmotionType.YELLOW, 1f))
        val eveningList = listOf(Pair(EmotionType.BLUE, 1f))
        val lateEveningList = emptyList<Pair<EmotionType, Float>>()

        binding = MoodScreenFragmentBinding.bind(view)

        val timeIntervals = listOf(
            Pair(binding.earlyMorning, binding.earlyMorningNumber to earlyMorningList),
            Pair(binding.morning, binding.morningNumber to morningList),
            Pair(binding.day, binding.dayNumber to dayList),
            Pair(binding.evening, binding.eveningNumber to eveningList),
            Pair(binding.lateEvening, binding.lateEveningNumber to lateEveningList)
        )

        timeIntervals.forEach { (emotionView, numberViewToList) ->
            val (numberView, emotionList) = numberViewToList
            emotionView.setEmotionList(emotionList)
            numberView.text = emotionList.size.toString()
        }
    }
}