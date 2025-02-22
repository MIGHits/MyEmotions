package com.example.firstlab.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.components.FeelingsRecyclerAdapter
import com.example.firstlab.databinding.FeelingsScreenBinding
import com.example.firstlab.databinding.StatisticScreenBinding

class StatisticScreen:Fragment(R.layout.statistic_screen) {
    private var binding: StatisticScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        StatisticScreenBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}