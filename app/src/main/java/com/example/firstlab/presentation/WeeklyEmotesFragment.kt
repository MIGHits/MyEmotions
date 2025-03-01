package com.example.firstlab.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.adapter.WeeklyEmotesAdapter
import com.example.firstlab.databinding.WeeklyEmotionsBinding
import com.example.firstlab.databinding.WeeklyEmotionsItemBinding
import com.example.firstlab.models.Emotion
import com.example.firstlab.models.WeeklyEmoteItem

class WeeklyEmotesFragment : Fragment(R.layout.weekly_emotions) {
    private lateinit var binding: WeeklyEmotionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeeklyEmotionsBinding.bind(view)

        val emoteAdapter = WeeklyEmotesAdapter()
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val recycler = binding.weeklyEmotesRecycler

        recycler.adapter = emoteAdapter
        recycler.layoutManager = manager
        recycler.suppressLayout(true)

        emoteAdapter.emotesList = listOf(
            WeeklyEmoteItem(
                "17 фев",
                "Понедельник",
                listOf(
                    Emotion(R.drawable.mithosis_emote, "Спокойствие"),
                    Emotion(R.drawable.lightning_emote, "Продуктивность"),
                    Emotion(R.drawable.ellipse_icon, "Счастье"),
                    Emotion(R.drawable.lightning_emote, "Удовлетворенность"),
                )
            ),
            WeeklyEmoteItem(
                "18 фев",
                "Вторник",
                listOf(
                    Emotion(R.drawable.shell_icon, "Выгорание"),
                )
            ),
            WeeklyEmoteItem(
                "19 фев",
                "Среда",
                listOf()
            ),
            WeeklyEmoteItem(
                "20 фев",
                "Четверг",
                listOf()
            ),
            WeeklyEmoteItem(
                "21 фев",
                "Пятница",
                listOf()
            ),
            WeeklyEmoteItem(
                "22 фев",
                "Суббота",
                listOf()
            ),
            WeeklyEmoteItem(
                "23 фев",
                "Воскресенье",
                listOf()
            ),
        )


    }
}