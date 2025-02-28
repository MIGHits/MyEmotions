package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.components.FragmentAdapterVertical
import com.example.firstlab.databinding.StatisticMainFragmentBinding

class StatisticMainFragment : Fragment(R.layout.statistic_main_fragment) {
    private lateinit var binding: StatisticMainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatisticMainFragmentBinding.bind(view)

        val fragments =
            listOf(
                EmotionsCategoryFragment(),
                EmotionsCategoryFragment(),
                EmotionsCategoryFragment(),
                EmotionsCategoryFragment()
            )
        val verticalAdapter = FragmentAdapterVertical(fragments, parentFragmentManager, lifecycle)
        binding.viewPagerVertical.adapter = verticalAdapter
        val indicator = binding.circleIndicator

        indicator.setViewPager(binding.viewPagerVertical)
        verticalAdapter.registerAdapterDataObserver(indicator.adapterDataObserver);
    }
}