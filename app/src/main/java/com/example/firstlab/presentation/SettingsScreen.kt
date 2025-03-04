package com.example.firstlab.presentation

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.adapter.NotificationAdapter
import com.example.firstlab.databinding.NotificationBottomSheetBinding
import com.example.firstlab.databinding.SettingsScreenBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar

class SettingsScreen : Fragment(R.layout.settings_screen) {
    private lateinit var binding: SettingsScreenBinding
    private lateinit var notificationList: MutableList<String>
    private val calendar = Calendar.getInstance()
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
        notificationList = mutableListOf(
            "20:00",
            "16:00",
            "20:00",
            "16:00",
        )
        notificationAdapter.notificationList = notificationList

        recycler.adapter = notificationAdapter
        recycler.layoutManager = manager

        val notificationButton = binding.addNotification
        notificationButton.setOnClickListener {
            showBottomDialog(notificationAdapter)
        }

    }


    private fun showBottomDialog(notificationAdapter: NotificationAdapter) {
        val notificationDialog = BottomSheetDialog(requireContext())

        val dialogBinding =
            NotificationBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))

        notificationDialog.setContentView(dialogBinding.root)
        val hours = dialogBinding.hours
        val minutes = dialogBinding.minutes

        hours.setText(calendar.get(Calendar.HOUR_OF_DAY).toString())
        minutes.setText(calendar.get(Calendar.MINUTE).toString())

        hours.setOnClickListener {
            showTimePicker(dialogBinding.hours, dialogBinding.minutes)
        }

        minutes.setOnClickListener {
            showTimePicker(dialogBinding.hours, dialogBinding.minutes)
        }

        dialogBinding.saveTime.setOnClickListener {

            notificationDialog.dismiss()
            val time = "${hours.text}:${minutes.text}"

            if (hours.text.isNotEmpty() &&
                minutes.text.isNotEmpty() &&
                !notificationList.contains(time)
            ) {
                notificationList.add(time)
                notificationAdapter.notifyItemInserted(notificationList.size - 1)
                binding.appBar.setExpanded(false, true)
            }
        }

        notificationDialog.show()
    }

    @SuppressLint("DefaultLocale")
    private fun showTimePicker(hoursView: EditText, minutesView: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.CustomTimePickerDialogTheme,
            { _, selectedHour, selectedMinute ->
                val formattedHours = String.format("%2d", selectedHour)
                val formattedMinutes = String.format("%02d", selectedMinute)

                hoursView.setText(formattedHours)
                minutesView.setText(formattedMinutes)

            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

}