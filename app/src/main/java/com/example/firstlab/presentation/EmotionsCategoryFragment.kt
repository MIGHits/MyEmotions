package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.databinding.EmotionsCategoryBinding
import com.example.firstlab.models.BallsItem
import com.example.firstlab.models.EmotesCategory
import com.example.firstlab.models.EmotionType

class EmotionsCategoryFragment : Fragment(R.layout.emotions_category) {
    private lateinit var binding: EmotionsCategoryBinding
    private var percentageList: List<EmotesCategory>? = null

    companion object {
        const val ARG_EMOTES_CATEGORY = "ARG_CATEGORY_LIST"

        fun setData(data: List<EmotesCategory>): StatisticMainFragment {
            return StatisticMainFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_EMOTES_CATEGORY, ArrayList(data))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        percentageList = arguments?.getParcelableArrayList(ARG_EMOTES_CATEGORY) ?: listOf(
            EmotesCategory(Pair(30f, EmotionType.GREEN)),
            EmotesCategory(Pair(15f, EmotionType.YELLOW)),
            EmotesCategory(Pair(5f, EmotionType.RED)),
            EmotesCategory(Pair(50f, EmotionType.BLUE)),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EmotionsCategoryBinding.bind(view)

        percentageList?.let { binding.bubbleChart.percentages = it }
    }
}