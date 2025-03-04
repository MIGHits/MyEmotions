package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.databinding.EmotionsCategoryBinding
import com.example.firstlab.models.EmotionType

class EmotionsCategoryFragment : Fragment(R.layout.emotions_category) {
    private lateinit var binding: EmotionsCategoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EmotionsCategoryBinding.bind(view)

        val percentageList = listOf(
            Pair(40f, EmotionType.GREEN),
            Pair(5f, EmotionType.YELLOW),
            Pair(5f, EmotionType.RED),
            Pair(50f, EmotionType.BLUE)
        )
        binding.bubbleChart.percentages = percentageList
    }
}