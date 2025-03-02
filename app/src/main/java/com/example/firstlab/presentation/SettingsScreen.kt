package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.firstlab.R
import com.example.firstlab.adapter.NotificationAdapter
import com.example.firstlab.databinding.SettingsScreenBinding

class SettingsScreen : Fragment(R.layout.settings_screen) {
    private lateinit var binding: SettingsScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SettingsScreenBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = SettingsScreenBinding.bind(view)
        val recycler = binding.notificationRecycler
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val notificationAdapter = NotificationAdapter()
        notificationAdapter.notificationList = mutableListOf(
            "20:00",
            "16:00",
            "20:00",
            "16:00",
            "20:00",
            "16:00",
            "20:00",
            "16:00",
            "20:00",
        )
        recycler.adapter = notificationAdapter
        recycler.layoutManager = manager
    }

}