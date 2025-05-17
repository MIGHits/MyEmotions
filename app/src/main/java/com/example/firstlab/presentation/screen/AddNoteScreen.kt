package com.example.firstlab.presentation.screen

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_ACTIVITIES_DATA
import com.example.firstlab.common.Constant.ARG_COMPANY_DATA
import com.example.firstlab.common.Constant.ARG_PLACES_DATA
import com.example.firstlab.common.Constant.CORNER_RADIUS
import com.example.firstlab.common.Constant.PADDING_MEDIUM
import com.example.firstlab.common.Constant.PADDING_SMALL
import com.example.firstlab.databinding.AddNoteScreenBinding
import com.example.firstlab.models.EmotionType
import com.example.firstlab.presentation.viewModel.CreateEmotionViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class AddNoteScreen : Fragment(R.layout.add_note_screen) {
    private var binding: AddNoteScreenBinding? = null
    private var company: List<String>? = null
    private var places: List<String>? = null
    private var activities: List<String>? = null
    private val viewModel: CreateEmotionViewModel by navGraphViewModels<CreateEmotionViewModel>(R.id.note_navigation_graph)

    companion object {
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

        lifecycleScope.launch {
            viewModel.createState.collect { state ->
                withContext(Dispatchers.Main) {
                    binding?.apply {
                        time.text = state.createTime?.let { getDateTime(it) }
                        emotion.text = state.name
                        state.iconRes?.let { feelingIcon.setBackgroundResource(it) }
                        when (state.emotionType) {
                            EmotionType.GREEN -> {
                                chosenFeelingCard.setBackgroundResource(R.drawable.green_type_gradient)
                                emotion.setTextColor(resources.getColor(R.color.greenGradient))
                            }

                            EmotionType.BLUE -> {
                                chosenFeelingCard.setBackgroundResource(R.drawable.blue_type_gradient)
                                emotion.setTextColor(resources.getColor(R.color.blueGradient))
                            }

                            EmotionType.YELLOW -> {
                                chosenFeelingCard.setBackgroundResource(R.drawable.yellow_type_gradient)
                                emotion.setTextColor(resources.getColor(R.color.yellowGradient))
                            }

                            EmotionType.RED -> {
                                chosenFeelingCard.setBackgroundResource(R.drawable.red_type_gradient)
                                emotion.setTextColor(resources.getColor(R.color.redGradient))
                            }

                            null -> {}
                        }
                    }
                }
            }
        }

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
            setPaddingRelative(
                PADDING_MEDIUM.dpToPx(),
                PADDING_SMALL.dpToPx(),
                PADDING_MEDIUM.dpToPx(),
                PADDING_SMALL.dpToPx()
            )
            text = name
            setTextAppearance(R.style.chipText)
            setTextColor(ContextCompat.getColor(context, R.color.white))
            chipCornerRadius = CORNER_RADIUS
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
                hint = context.getString(R.string.edit_text_hint)
                textSize = 14f
                setTextColor(Color.BLACK)
                setHintTextColor(Color.GRAY)
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.add_button_shape)
                setPadding(
                    PADDING_MEDIUM.dpToPx(),
                    PADDING_SMALL.dpToPx(),
                    PADDING_MEDIUM.dpToPx(),
                    PADDING_SMALL.dpToPx()
                )


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

    fun getDateTime(millis: Long): String {
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime = Instant.ofEpochMilli(millis).atZone(zoneId)

        val today = LocalDate.now(zoneId)

        when (zonedDateTime.toLocalDate()) {
            today -> return getString(
                R.string.today,
                zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            )

            today.minusDays(1) -> return getString(
                R.string.yesterday,
                zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        }

        val weekStart = today.minusDays(today.dayOfWeek.value.toLong() - 1)
        val weekEnd = weekStart.plusDays(6)

        return if (zonedDateTime.toLocalDate() in weekStart..weekEnd) {
            val dayOfWeek = zonedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ru"))
            val time = zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            "$dayOfWeek,$time"
        } else {
            zonedDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm"))
        }
    }
}