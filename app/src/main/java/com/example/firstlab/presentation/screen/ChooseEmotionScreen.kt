package com.example.firstlab.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_BALLS_DATA
import com.example.firstlab.common.Constant.BALLS
import com.example.firstlab.common.Constant.GRID_SIZE
import com.example.firstlab.databinding.EmotionsChooseBinding
import com.example.firstlab.presentation.adapter.BallsRecyclerAdapter
import com.example.firstlab.presentation.models.BallsItem
import com.example.firstlab.presentation.viewModel.CreateEmotionViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ChooseEmotionScreen : Fragment(R.layout.emotions_choose) {
    private var binding: EmotionsChooseBinding? = null
    private var emotionColor: Int? = null
    private lateinit var ballsAdapter: BallsRecyclerAdapter
    private var ballsList: List<BallsItem>? = null
    private val viewModel: CreateEmotionViewModel by activityViewModel<CreateEmotionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ballsList = arguments?.getParcelableArrayList(ARG_BALLS_DATA) ?: BALLS
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = EmotionsChooseBinding.bind(view)

        ballsAdapter =
            BallsRecyclerAdapter({ color, name ->
                showEmotionInfo(color, name)
                emotionColor = color
            },
                { hideEmotionInfo() })



        ballsList?.let { ballsAdapter.ballsList = it }
        binding?.ballsRecycler?.adapter = ballsAdapter

        val manager = GridLayoutManager(requireContext(), GRID_SIZE)

        binding?.ballsRecycler?.layoutManager = manager

        binding?.continueButton?.isEnabled = false

        binding?.backToFeelings?.setOnClickListener {
            view.findNavController()
                .navigate(R.id.chooseEmotionScreenToNavigationActivity)
        }
        binding?.apply {
            continueButton.setOnClickListener {
                val name = emotionName.text.toString()
                viewModel.chooseEmotion(
                    name = name,
                    color = emotionColor ?: R.color.circleSecondaryColor
                )
                view.findNavController().navigate(
                    ChooseEmotionScreenDirections.actionChooseEmotionScreen2ToAddNoteScreen()
                )
            }
        }
    }

    private fun showEmotionInfo(color: Int, name: String) {
        binding?.apply {
            emotionStub.visibility = View.GONE
            emotionInfo.visibility = View.VISIBLE

            emotionName.setTextColor(color)
            emotionName.text = name
            emotionDescription.text = getString(R.string.emotion_description)
            continueButton.isEnabled = true
        }
    }

    private fun hideEmotionInfo() {
        binding?.apply {
            emotionInfo.visibility = View.GONE
            emotionStub.visibility = View.VISIBLE
            continueButton.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}