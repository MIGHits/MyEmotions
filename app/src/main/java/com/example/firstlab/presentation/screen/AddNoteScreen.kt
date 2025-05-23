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
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ACTIVITIES
import com.example.firstlab.common.Constant.COMPANY
import com.example.firstlab.common.Constant.CORNER_RADIUS
import com.example.firstlab.common.Constant.MINIMUM_CONST
import com.example.firstlab.common.Constant.PADDING_MEDIUM
import com.example.firstlab.common.Constant.PADDING_SMALL
import com.example.firstlab.common.Constant.PLACES
import com.example.firstlab.databinding.AddNoteScreenBinding
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.extension.navigateWithClearBackStack
import com.example.firstlab.presentation.viewModel.CreateEmotionViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AddNoteScreen : Fragment(R.layout.add_note_screen) {
    private var binding: AddNoteScreenBinding? = null
    private val args by navArgs<AddNoteScreenArgs>()
    private val company: MutableSet<String> = COMPANY
    private val places: MutableSet<String> = PLACES
    private val activities: MutableSet<String> = ACTIVITIES
    private val viewModel: CreateEmotionViewModel by activityViewModel<CreateEmotionViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.emotionId != MINIMUM_CONST) {
            viewModel.getEmotionDetails(args.emotionId)
        }

        lifecycleScope.launch {
            viewModel.createState.collect { state ->
                withContext(Dispatchers.Main) {
                    binding?.apply {
                        time.text = state.createTime
                        emotion.text = state.name

                        state.actions.forEach { action ->
                            addNewChip(
                                activitiesChipGroup,
                                action
                            ) { newAction -> activities.add(newAction) }
                            chooseChip(activitiesChipGroup, action)
                        }

                        state.company.forEach { companion ->
                            addNewChip(
                                companyChipGroup,
                                companion,
                                { newCompanion -> company.add(newCompanion) })
                            chooseChip(companyChipGroup, companion)
                        }

                        state.location.forEach { location ->
                            addNewChip(
                                placeChipGroup,
                                location,
                                { newLocation -> places.add(newLocation) })
                            chooseChip(placeChipGroup, location)
                        }

                        company.addAll(state.company)
                        places.addAll(state.location)

                        state.iconRes?.let { feelingIcon.setBackgroundResource(it) }
                        when (state.type) {
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

        val activitiesChipGroup = binding?.activitiesChipGroup
        val companyChipGroup = binding?.companyChipGroup
        val placeChipGroup = binding?.placeChipGroup

        binding?.saveButton?.setOnClickListener {
            viewModel.addEmotion(
                actions = activitiesChipGroup?.children?.filter { it is Chip && it.isChecked }
                    ?.map { (it as Chip).text.toString() }?.toList() ?: emptyList(),
                company = companyChipGroup?.children?.filter { it is Chip && it.isChecked }
                    ?.map { (it as Chip).text.toString() }?.toList() ?: emptyList(),
                places = placeChipGroup?.children?.filter { it is Chip && it.isChecked }
                    ?.map { (it as Chip).text.toString() }?.toList() ?: emptyList()
            )
            findNavController().navigateWithClearBackStack(R.id.back_to_journal)
        }


        activities.toList().forEach { activity ->
            addNewChip(
                activitiesChipGroup,
                activity
            ) { newActivity -> activities.add(newActivity) }
        }

        company.toList().forEach { companion ->
            addNewChip(companyChipGroup, companion)
            { newCompany -> company.add(newCompany) }
        }

        places.toList().forEach { place ->
            addNewChip(placeChipGroup, place)
            { newPlace -> places.add(newPlace) }
        }

        binding?.addActivityButton?.setOnClickListener {
            showEditText(activitiesChipGroup) { newActivity -> activities.add(newActivity) }
        }
        binding?.addCompanionButton?.setOnClickListener {
            showEditText(companyChipGroup) { newCompany -> company.add(newCompany) }
        }
        binding?.addPlaceButton?.setOnClickListener {
            showEditText(placeChipGroup) { newPlace -> places.add(newPlace) }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun addNewChip(chipGroup: ChipGroup?, name: String, addToList: (String) -> Unit) {
        val exists = chipGroup?.children
            ?.filterIsInstance<Chip>()
            ?.any { it.text.toString().equals(name, ignoreCase = true) } ?: false

        if (exists) return

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
        addToList(name)
    }

    private fun chooseChip(chipGroup: ChipGroup?, name: String) {
        chipGroup?.children
            ?.filterIsInstance<Chip>()
            ?.firstOrNull { it.text.toString() == name }
            ?.isChecked = true
    }

    private fun showEditText(chipGroup: ChipGroup?, addToList: (String) -> Unit) {
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

                            addNewChip(group, inputText, addToList)
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