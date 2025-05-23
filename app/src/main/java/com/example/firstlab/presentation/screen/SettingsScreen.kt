package com.example.firstlab.presentation.screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

        val logoutButton = binding.logoutIcon
        logoutButton.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireContext(), AuthActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                toggleNotificationSwitch()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.notification_permission_toast), Toast.LENGTH_SHORT).show()
                binding.notificationSwitcher.isChecked = false
            }
        }

        binding.notificationSwitcher.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    toggleNotificationSwitch()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                toggleNotificationSwitch()
            }
        }

        binding.fingerprintSwitcher.setOnClickListener {
            val state = viewModel.settingsState.value as? SettingsState.Content ?: return@setOnClickListener
            viewModel.toggleSwitch(
                state.content.user.copy(
                    isFingerprintEnabled = !state.content.user.isFingerprintEnabled
                )
            )
        }

        lifecycleScope.launch {
            viewModel.settingsState.collect { state ->
                when (state) {
                    is SettingsState.Content -> {
                        binding.apply {
                            loadingOverlay.visibility = View.GONE
                            notificationSwitcher.isChecked = state.content.user.isNotificationEnabled
                            fingerprintSwitcher.isChecked = state.content.user.isFingerprintEnabled
                            userName.text = state.content.user.username
                            Glide.with(this@SettingsScreen)
                                .load(state.content.user.avatar)
                                .into(avatar)
                            addNotification.setOnClickListener {
                                showBottomDialog(state.content)
                            }
                            notificationAdapter.notificationList =
                                state.content.notifications.toMutableList()
                        }
                    }

                    SettingsState.Loading -> {
                        binding.loadingOverlay.visibility = View.VISIBLE
                    }

                    SettingsState.Initial -> {}
                }
            }
        }
    }

    private fun toggleNotificationSwitch() {
        val state = viewModel.settingsState.value as? SettingsState.Content ?: return
        viewModel.toggleSwitch(
            state.content.user.copy(
                isNotificationEnabled = !state.content.user.isNotificationEnabled
            )
        )
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
