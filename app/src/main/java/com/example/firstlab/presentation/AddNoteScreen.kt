package com.example.firstlab.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firstlab.R
import com.example.firstlab.databinding.AddNoteScreenBinding
import com.example.firstlab.models.FeelingItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class AddNoteScreen : Fragment(R.layout.add_note_screen) {
    private var binding: AddNoteScreenBinding? = null
    private var company: List<String>? = null
    private var places: List<String>? = null
    private var activities: List<String>? = null

    companion object {
        const val ARG_COMPANY_DATA = "ARG_COMPANY_LIST"
        const val ARG_PLACES_DATA = "ARG_PLACES_LIST"
        const val ARG_ACTIVITIES_DATA = "ARG_ACTIVITIES_LIST"

        fun setData(
            companyList: List<String>,
            placesList: List<String>,
            activitiesList: List<String>
        ): AddNoteScreen {
            return AddNoteScreen().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_COMPANY_DATA, ArrayList(companyList))
                    putStringArrayList(ARG_PLACES_DATA, ArrayList(placesList))
                    putStringArrayList(ARG_ACTIVITIES_DATA, ArrayList(activitiesList))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activities = arguments?.getStringArrayList(ARG_ACTIVITIES_DATA) ?: listOf(
            "Приём пищи",
            "Встреча с друзьями",
            "Тренировка",
            "Хобби",
            "Отдых",
            "Поездка"
        )

        company = arguments?.getStringArrayList(ARG_COMPANY_DATA) ?: listOf(
            "Один",
            "Друзья",
            "Семья",
            "Коллеги",
            "Партнёр",
            "Питомцы"
        )

        places = arguments?.getStringArrayList(ARG_PLACES_DATA) ?: listOf(
            "Дом",
            "Работа",
            "Школа",
            "Транспорт",
            "Улица"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = AddNoteScreenBinding.bind(view)

        binding?.backToBubbles?.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding?.saveButton?.setOnClickListener {
            view.findNavController().navigate(R.id.addNoteScreenToNavigationActivity)
        }


        val activitiesChipGroup = binding?.activitiesChipGroup


        activities?.forEach { activity ->
            addNewChip(activitiesChipGroup, activity)
        }

        val companyChipGroup = binding?.companyChipGroup

        company?.forEach { companion ->
            addNewChip(companyChipGroup, companion)
        }

        val placeChipGroup = binding?.placeChipGroup

        places?.forEach { place ->
            addNewChip(placeChipGroup, place)
        }


        binding?.addActivityButton?.setOnClickListener {
            showEditText(activitiesChipGroup)
        }
        binding?.addCompanionButton?.setOnClickListener {
            showEditText(companyChipGroup)
        }
        binding?.addPlaceButton?.setOnClickListener {
            showEditText(placeChipGroup)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun addNewChip(chipGroup: ChipGroup?, name: String) {
        val chip = Chip(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setChipBackgroundColorResource(R.color.chip_group_color_selector)
            isCheckable = true
            setPaddingRelative(16.dpToPx(), 8.dpToPx(), 16.dpToPx(), 8.dpToPx())
            text = name
            setTextAppearance(R.style.chipText)
            setTextColor(ContextCompat.getColor(context, R.color.white))
            chipCornerRadius = 32f
            chipStrokeColor = ColorStateList.valueOf(Color.TRANSPARENT)
            setEnsureMinTouchTargetSize(false)
        }
        chipGroup?.addView(chip, chipGroup.childCount - 1)
    }

    private fun showEditText(chipGroup: ChipGroup?) {
        chipGroup?.let { group ->

            val lastIndex = group.childCount - 1
            val lastChip = group.getChildAt(lastIndex) as? Chip
            lastChip?.visibility = View.GONE


            val editText = EditText(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                inputType = InputType.TYPE_CLASS_TEXT
                hint = "Введите текст"
                textSize = 14f
                setTextColor(Color.BLACK)
                setHintTextColor(Color.GRAY)
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.add_button_shape)
                setPadding(16.dpToPx(), 8.dpToPx(), 16.dpToPx(), 8.dpToPx())


                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val inputText = text.toString()
                        if (inputText.isNotEmpty()) {

                            addNewChip(group, inputText)
                        }


                        lastChip?.visibility = View.VISIBLE
                        group.removeView(this)


                        val imm =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(windowToken, 0)

                        true
                    } else {
                        false
                    }
                }
            }


            group.addView(editText, lastIndex)


            editText.requestFocus()
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}