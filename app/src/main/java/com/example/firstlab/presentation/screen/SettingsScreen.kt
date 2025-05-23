package com.example.firstlab.presentation.screen

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.adapter.NotificationAdapter
import com.example.firstlab.databinding.NotificationBottomSheetBinding
import com.example.firstlab.databinding.SettingsScreenBinding
import com.example.firstlab.presentation.models.NotificationPresentationModel
import com.example.firstlab.presentation.models.ProfileSettings
import com.example.firstlab.presentation.state.SettingsState
import com.example.firstlab.presentation.viewModel.SettingsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class SettingsScreen : Fragment(R.layout.settings_screen) {
    private lateinit var binding: SettingsScreenBinding
    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SettingsScreenBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recycler = binding.notificationRecycler
        val manager = LinearLayoutManager(requireContext())
        val notificationAdapter = NotificationAdapter { notification ->
            viewModel.removeNotification(notification)
        }

        recycler.adapter = notificationAdapter
        recycler.layoutManager = manager

        lifecycleScope.launch {
            viewModel.settingsState.collect { state ->
                when (state) {
                    is SettingsState.Content -> {
                        notificationAdapter.notificationList =
                            state.content.notifications.toMutableList()
                        binding.apply {
                            notificationSwitcher.isChecked =
                                state.content.user.isNotificationEnabled
                            fingerprintSwitcher.isChecked = state.content.user.isFingerprintEnabled
                            userName.text = state.content.user.username

                            addNotification.setOnClickListener {
                                showBottomDialog(state.content)
                            }
                            notificationSwitcher.setOnClickListener {
                                viewModel.toggleSwitch(
                                    state.content.user.copy(
                                        isNotificationEnabled = !state.content.user.isNotificationEnabled
                                    )
                                )
                            }

                            fingerprintSwitcher.setOnClickListener {
                                viewModel.toggleSwitch(
                                    state.content.user.copy(
                                        isFingerprintEnabled = !state.content.user.isFingerprintEnabled
                                    )
                                )
                            }
                        }
                    }

                    SettingsState.Initial -> {}
                    SettingsState.Loading -> {}
                }
            }
        }
    }

    private fun showBottomDialog(profileSettings: ProfileSettings) {
        val notificationDialog = BottomSheetDialog(requireContext())
        val dialogBinding =
            NotificationBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
        notificationDialog.setContentView(dialogBinding.root)

        val hours = dialogBinding.hours
        val minutes = dialogBinding.minutes

        val (hourStr, minuteStr) = profileSettings.selectedTime.split(":")
        hours.setText(hourStr)
        minutes.setText(minuteStr)

        lifecycleScope.launch {
            viewModel.settingsState.collect { state ->
                if (state is SettingsState.Content) {
                    val (newHour, newMinute) = state.content.selectedTime.split(":")
                    hours.setText(newHour)
                    minutes.setText(newMinute)
                }
            }
        }

        val openTimePicker = {
            showTimePicker(profileSettings)
        }

        hours.setOnClickListener { openTimePicker() }
        minutes.setOnClickListener { openTimePicker() }

        dialogBinding.saveTime.setOnClickListener {
            val time = "${hours.text}:${minutes.text}"
            viewModel.createNotification(NotificationPresentationModel(time = time))
            notificationDialog.dismiss()
        }

        notificationDialog.show()
    }

    @SuppressLint("DefaultLocale")
    private fun showTimePicker(state: ProfileSettings) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.CustomTimePickerDialogTheme,
            { _, selectedHour, selectedMinute ->
                viewModel.setTime(state, selectedHour, selectedMinute)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}
