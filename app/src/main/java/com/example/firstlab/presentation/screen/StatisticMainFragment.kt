package com.example.firstlab.presentation.screen

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.presentation.adapter.FragmentAdapterVertical
import com.example.firstlab.common.Constant.ARG_MAIN_STATISTIC
import com.example.firstlab.common.Constant.VIEW_PAGER_OFFSET
import com.example.firstlab.common.Constant.ZERO_CONST
import com.example.firstlab.databinding.StatisticMainFragmentBinding
import com.example.firstlab.presentation.models.EmotesCategory

class StatisticMainFragment : Fragment(R.layout.statistic_main_fragment) {
    private lateinit var binding: StatisticMainFragmentBinding

    companion object {
        fun setData(data: List<EmotesCategory>): StatisticMainFragment {
            return StatisticMainFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_MAIN_STATISTIC, ArrayList(data))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatisticMainFragmentBinding.bind(view)

        val fragments =
            listOf(
                EmotionsCategoryFragment.setData(emptyList()),
                WeeklyEmotesFragment.setData(emptyList()),
                MostFrequentEmotesFragment.setData(emptyList()),
                MoodStatisticFragment.setData(emptyList())
            )

        val verticalAdapter = FragmentAdapterVertical(this, fragments)
        val verticalViewPager = binding.viewPagerVertical

        verticalViewPager.adapter = verticalAdapter
        verticalViewPager.offscreenPageLimit = 1

        verticalViewPager.setPadding(
            ZERO_CONST, ZERO_CONST, ZERO_CONST, VIEW_PAGER_OFFSET.dpToPx().toInt()
        )

        val indicator = binding.circleIndicator
        indicator.setViewPager(binding.viewPagerVertical)
        verticalAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
    }

    private fun Int.dpToPx(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, toFloat(), Resources.getSystem().displayMetrics
        )
    }
}