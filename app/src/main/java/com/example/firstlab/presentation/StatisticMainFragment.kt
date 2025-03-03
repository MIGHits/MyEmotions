package com.example.firstlab.presentation

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.firstlab.R
import com.example.firstlab.adapter.FragmentAdapterVertical
import com.example.firstlab.databinding.StatisticMainFragmentBinding

class StatisticMainFragment : Fragment(R.layout.statistic_main_fragment) {
    private lateinit var binding: StatisticMainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatisticMainFragmentBinding.bind(view)

        val fragments =
            listOf(
                EmotionsCategoryFragment(),
                WeeklyEmotesFragment(),
                MostFrequentEmotesFragment(),
                MoodStatisticFragment()
            )
        val verticalAdapter = FragmentAdapterVertical(this, fragments)
        val verticalViewPager = binding.viewPagerVertical
        verticalViewPager.adapter = verticalAdapter
        verticalViewPager.offscreenPageLimit = 1
        verticalViewPager.setPadding(
            0, 0, 0, 70.dpToPx().toInt()
        )

        val indicator = binding.circleIndicator
        indicator.setViewPager(binding.viewPagerVertical)
        verticalAdapter.registerAdapterDataObserver(indicator.adapterDataObserver);
    }

    private fun Int.dpToPx(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, toFloat(), Resources.getSystem().displayMetrics
        )
    }
}