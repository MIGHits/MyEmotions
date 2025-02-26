package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firstlab.R
import com.example.firstlab.databinding.SettingsScreenBinding

class SettingsScreen:Fragment(R.layout.settings_screen) {
    private var binding:SettingsScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SettingsScreenBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}