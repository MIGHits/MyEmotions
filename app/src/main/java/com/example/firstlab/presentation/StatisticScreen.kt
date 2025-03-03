package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.adapter.FragmentAdapterHorizontal
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

        val fragments = listOf(
            StatisticMainFragment(),
            StatisticMainFragment(),
            StatisticMainFragment(),
            StatisticMainFragment()
        )

        val daysList = listOf("17-23 фев", "10–16 фев", "3–9 фев", "27 янв – 2 фев")
        val horizontalAdapter =
            FragmentAdapterHorizontal(this,fragments)
        binding.viewPagerHorizontal.adapter = horizontalAdapter

        TabLayoutMediator(binding.daysTab, binding.viewPagerHorizontal) { tab, position ->
            tab.text = daysList[position]
        }.attach()
    }
}