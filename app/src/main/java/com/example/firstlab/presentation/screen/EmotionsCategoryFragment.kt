package com.example.firstlab.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_EMOTES_CATEGORY
import com.example.firstlab.common.Constant.ARG_NOTE_COUNT
import com.example.firstlab.databinding.EmotionsCategoryBinding
import com.example.firstlab.presentation.models.EmotesCategory
import kotlin.properties.Delegates

class EmotionsCategoryFragment : Fragment(R.layout.emotions_category) {
    private lateinit var binding: EmotionsCategoryBinding
    private lateinit var percentageList: List<EmotesCategory>
    private var noteCount by Delegates.notNull<Int>()

    companion object {

        fun setData(data: List<EmotesCategory>, noteCount: Int): EmotionsCategoryFragment {
            return EmotionsCategoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_EMOTES_CATEGORY, ArrayList(data))
                    putInt(ARG_NOTE_COUNT, noteCount)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        percentageList = arguments?.getParcelableArrayList(ARG_EMOTES_CATEGORY) ?: emptyList()
        noteCount = arguments?.getInt(ARG_NOTE_COUNT) ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EmotionsCategoryBinding.bind(view)
        binding.noteCounter.text = resources.getQuantityString(
            R.plurals.posts_count,
            noteCount,
            noteCount
        )
        percentageList.let { binding.bubbleChart.percentages = it }
    }
}