package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_MOOD_DATA
import com.example.firstlab.databinding.MoodScreenFragmentBinding
import com.example.firstlab.databinding.MostFrequentEmotesBinding
import com.example.firstlab.models.EmotesCategory
import com.example.firstlab.models.Emotion
import com.example.firstlab.models.EmotionType
import com.example.firstlab.models.TimeOfDay

class MoodStatisticFragment : Fragment(R.layout.mood_screen_fragment) {
    private lateinit var binding: MoodScreenFragmentBinding
    private var moodList: List<EmotesCategory> = emptyList()

    companion object {

        fun setData(data: List<EmotesCategory>): MoodStatisticFragment {
            return MoodStatisticFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_MOOD_DATA, ArrayList(data))
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moodList = arguments?.getParcelableArrayList(ARG_MOOD_DATA) ?: listOf(
            EmotesCategory(Pair(1f, EmotionType.GREEN), TimeOfDay.EARLY_MORNING),
            EmotesCategory(Pair(0.5f, EmotionType.YELLOW), TimeOfDay.MORNING),
            EmotesCategory(Pair(0.5f, EmotionType.RED), TimeOfDay.MORNING),
            EmotesCategory(Pair(1f, EmotionType.RED), TimeOfDay.DAY),
            EmotesCategory(Pair(1f, EmotionType.BLUE), TimeOfDay.EVENING),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoodScreenFragmentBinding.bind(view)

        val timeIntervals = listOf(
            TimeOfDay.EARLY_MORNING to (binding.earlyMorning to binding.earlyMorningNumber),
            TimeOfDay.MORNING to (binding.morning to binding.morningNumber),
            TimeOfDay.DAY to (binding.day to binding.dayNumber),
            TimeOfDay.EVENING to (binding.evening to binding.eveningNumber),
            TimeOfDay.LATE_EVENING to (binding.lateEvening to binding.lateEveningNumber)
        )

        timeIntervals.forEach { (timeOfDay, views) ->
            val (emotionView, numberView) = views
            val emotions = moodList.filter { it.timeOfDay == timeOfDay }
            emotionView.setEmotionList(emotions)
            numberView.text = emotions.size.toString()
        }
    }
}
