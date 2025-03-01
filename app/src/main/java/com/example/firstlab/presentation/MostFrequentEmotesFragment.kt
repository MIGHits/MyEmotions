package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.adapter.EmotesFrequencyAdapter
import com.example.firstlab.databinding.MostFrequentEmotesBinding
import com.example.firstlab.databinding.MostFrequentItemBinding
import com.example.firstlab.models.Emotion
import com.example.firstlab.models.EmotionType

class MostFrequentEmotesFragment : Fragment(R.layout.most_frequent_emotes) {
    private lateinit var binding: MostFrequentEmotesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MostFrequentEmotesBinding.bind(view)

        val adapter = EmotesFrequencyAdapter()
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val emotesRecycler = binding.weeklyEmotesRecycler

        emotesRecycler.adapter = adapter
        adapter.emotesList =
            listOf(
                Emotion(R.drawable.mithosis_emote, "Спокойствие", 2, EmotionType.GREEN),
                Emotion(R.drawable.lightning_emote, "Продуктивность", 1, EmotionType.YELLOW),
                Emotion(R.drawable.ellipse_icon, "Счастье", 4, EmotionType.YELLOW),
                Emotion(R.drawable.shell_icon, "Усталость", 1, EmotionType.BLUE)

            )
        emotesRecycler.layoutManager = manager
    }
}