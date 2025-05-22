package com.example.firstlab.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.firstlab.R
import com.example.firstlab.common.Constant.RELEASE_YEAR
import com.example.firstlab.presentation.adapter.FragmentAdapterHorizontal
import com.example.firstlab.common.Constant.ZERO_CONST
import com.example.firstlab.databinding.StatisticScreenBinding
import com.example.firstlab.presentation.models.WeekInfo
import com.example.firstlab.presentation.viewModel.StatisticsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class StatisticScreen : Fragment(R.layout.statistic_screen) {
    private lateinit var binding: StatisticScreenBinding
    private lateinit var weeksList: List<WeekInfo>
    private var currentWeekIndex: Int? = null
    private val viewModel: StatisticsViewModel by activityViewModel<StatisticsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weekInfo = viewModel.getWeeksInfo()
        weeksList = weekInfo.first
        currentWeekIndex = weekInfo.second
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatisticScreenBinding.bind(view)

        val currentIndex = currentWeekIndex ?: 0
        viewModel.getWeekData(currentIndex)
        if (currentIndex > 0) {
            viewModel.getWeekData(currentIndex - 1)
        }
        if (currentIndex < weeksList.size - 1) {
            viewModel.getWeekData(currentIndex + 1)
        }

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            binding.viewPagerHorizontal.setPadding(
                ZERO_CONST,
                ZERO_CONST,
                ZERO_CONST,
                -systemBars.bottom
            )
            insets
        }

        val fragments =
            List(weeksList.size) { index: Int -> StatisticMainFragment.setData(index) }
        val horizontalAdapter = FragmentAdapterHorizontal(this, fragments)
        binding.apply {
            viewPagerHorizontal.adapter = horizontalAdapter
            viewPagerHorizontal.offscreenPageLimit = 2
            viewPagerHorizontal.setCurrentItem(currentWeekIndex ?: 0, false)
            viewPagerHorizontal.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position > 0) {
                        viewModel.getWeekData(position - 1)
                    }
                    viewModel.getWeekData(position)
                    if (position < weeksList.size - 1) {
                        viewModel.getWeekData(position + 1)
                    }
                }
            })
            TabLayoutMediator(binding.daysTab, viewPagerHorizontal) { tab, position ->
                tab.text = weeksList[position].weekString
            }.attach()
        }
    }
}