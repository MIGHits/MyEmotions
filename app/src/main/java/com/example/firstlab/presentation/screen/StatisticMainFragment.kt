package com.example.firstlab.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_WEEK_INDEX
import com.example.firstlab.presentation.adapter.FragmentAdapterVertical
import com.example.firstlab.common.Constant.VIEW_PAGER_OFFSET
import com.example.firstlab.common.Constant.ZERO_CONST
import com.example.firstlab.databinding.StatisticMainFragmentBinding
import com.example.firstlab.extension.dpToPx
import com.example.firstlab.presentation.state.StatisticsState
import com.example.firstlab.presentation.viewModel.StatisticsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StatisticMainFragment : Fragment(R.layout.statistic_main_fragment) {

    private lateinit var binding: StatisticMainFragmentBinding
    private lateinit var verticalAdapter: FragmentAdapterVertical
    private val viewModel: StatisticsViewModel by activityViewModel<StatisticsViewModel>()

    private var weekIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weekIndex = arguments?.getInt(ARG_WEEK_INDEX) ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatisticMainFragmentBinding.bind(view)

        val verticalViewPager = binding.viewPagerVertical
        verticalAdapter = FragmentAdapterVertical(this, emptyList())
        verticalViewPager.adapter = verticalAdapter
        verticalViewPager.offscreenPageLimit = 1
        verticalViewPager.setPadding(
            ZERO_CONST, ZERO_CONST, ZERO_CONST, VIEW_PAGER_OFFSET.dpToPx().toInt()
        )

        val indicator = binding.circleIndicator
        indicator.setViewPager(verticalViewPager)
        verticalAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        lifecycleScope.launch {
            viewModel.getStatisticsStateForWeek(weekIndex).collect { state ->
                when (state) {
                    is StatisticsState.Content -> {
                        binding.loadingOverlay.visibility = View.GONE
                        val fragments = listOf(
                            EmotionsCategoryFragment.setData(
                                state.content.emotionsCategory,
                                state.content.noteCount
                            ),
                            WeeklyEmotesFragment.setData(state.content.weeklyEmotions),
                            MostFrequentEmotesFragment.setData(state.content.mostFrequentEmotions),
                            MoodStatisticFragment.setData(state.content.dailyMood)
                        )
                        if (verticalAdapter.fragments != fragments) {
                            verticalAdapter.updateFragments(fragments)
                        }
                    }

                    is StatisticsState.Empty -> {
                        val fragments = listOf(
                            EmotionsCategoryFragment.setData(emptyList(), 0),
                            WeeklyEmotesFragment.setData(emptyList()),
                            MostFrequentEmotesFragment.setData(emptyList()),
                            MoodStatisticFragment.setData(emptyList())
                        )
                        if (verticalAdapter.fragments != fragments) {
                            verticalAdapter.updateFragments(fragments)
                        }
                    }

                    is StatisticsState.Loading -> {
                        binding.loadingOverlay.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    companion object {

        fun setData(weekIndex: Int): StatisticMainFragment {
            return StatisticMainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_WEEK_INDEX, weekIndex)
                }
            }
        }
    }
}

