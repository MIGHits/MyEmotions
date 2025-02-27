package com.example.firstlab.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.components.FeelingsRecyclerAdapter
import com.example.firstlab.components.FragmentAdapter
import com.example.firstlab.databinding.FeelingsScreenBinding
import com.example.firstlab.databinding.StatisticScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class StatisticScreen : Fragment(R.layout.statistic_screen) {
    private lateinit var binding: StatisticScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatisticScreenBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragments = listOf(AddNoteScreen(), AddNoteScreen(), AddNoteScreen(), AddNoteScreen())
        val daysList = listOf("17-23 фев", "10–16 фев", "3–9 фев", "27 янв – 2 фев")
        val horizontalAdapter = FragmentAdapter(fragments, parentFragmentManager, lifecycle)

        binding.viewPagerHorizontal.adapter = horizontalAdapter
        TabLayoutMediator(binding.daysTab, binding.viewPagerHorizontal) { tab, position ->
            tab.text = daysList[position]
        }.attach()
    }
}