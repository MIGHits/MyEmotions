package com.example.firstlab.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_WEEKLY_EMOTES
import com.example.firstlab.databinding.WeeklyEmotionsBinding
import com.example.firstlab.presentation.adapter.WeeklyEmotesAdapter
import com.example.firstlab.domain.entity.WeeklyEmoteItem

class WeeklyEmotesFragment : Fragment(R.layout.weekly_emotions) {
    private lateinit var binding: WeeklyEmotionsBinding
    private var weeklyEmotesList: List<WeeklyEmoteItem>? = null

    companion object {

        fun setData(data: List<WeeklyEmoteItem>): WeeklyEmotesFragment {
            return WeeklyEmotesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_WEEKLY_EMOTES, ArrayList(data))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weeklyEmotesList = arguments?.getParcelableArrayList(ARG_WEEKLY_EMOTES) ?: emptyList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeeklyEmotionsBinding.bind(view)

        val emoteAdapter = WeeklyEmotesAdapter()
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val recycler = binding.weeklyEmotesRecycler

        recycler.adapter = emoteAdapter
        recycler.layoutManager = manager
        recycler.suppressLayout(false)

        weeklyEmotesList?.let { emoteAdapter.emotesList = it }

    }
}