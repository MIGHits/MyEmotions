package com.example.firstlab.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_FREQUENT_DATA
import com.example.firstlab.databinding.MostFrequentEmotesBinding
import com.example.firstlab.presentation.adapter.EmotesFrequencyAdapter
import com.example.firstlab.presentation.models.Emotion

class MostFrequentEmotesFragment : Fragment(R.layout.most_frequent_emotes) {
    private lateinit var binding: MostFrequentEmotesBinding
    private var frequentList: List<Emotion> = emptyList()

    companion object {


        fun setData(data: List<Emotion>): MostFrequentEmotesFragment {
            return MostFrequentEmotesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FREQUENT_DATA, ArrayList(data))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frequentList = arguments?.getParcelableArrayList(ARG_FREQUENT_DATA) ?: emptyList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MostFrequentEmotesBinding.bind(view)

        val adapter = EmotesFrequencyAdapter()
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val emotesRecycler = binding.frequentEmotesRecycler

        emotesRecycler.adapter = adapter
        frequentList.let {
            adapter.emotesList = it
        }
        emotesRecycler.layoutManager = manager
    }
}