package com.example.firstlab.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstlab.R
import com.example.firstlab.components.BallsRecyclerAdapter
import com.example.firstlab.databinding.EmotionsChooseBinding
import com.example.firstlab.models.BallsItem

class ChooseEmotionScreen : Fragment(R.layout.emotions_choose) {
    private var binding: EmotionsChooseBinding? = null
    private lateinit var ballsAdapter: BallsRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = EmotionsChooseBinding.bind(view)

        ballsAdapter =
            BallsRecyclerAdapter({ color, name -> showEmotionInfo(color, name) },
                { hideEmotionInfo() })

        val bubbles = listOf(
            BallsItem(ContextCompat.getColor(requireContext(), R.color.redGradient), "Ярость"),
            BallsItem(ContextCompat.getColor(requireContext(), R.color.redGradient), "Напряжение"),
            BallsItem(
                ContextCompat.getColor(requireContext(), R.color.yellowGradient),
                "Возбуждение"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.yellowGradient)),
                "Восторг"
            ),
            BallsItem(ContextCompat.getColor(requireContext(), R.color.redGradient), "Зависть"),
            BallsItem(
                ContextCompat.getColor(requireContext(), R.color.redGradient),
                "Беспокойство"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.yellowGradient)),
                "Уверенность"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.yellowGradient)),
                "Счастье"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.blueGradient)),
                "Выгопание"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.blueGradient)),
                "Усталость"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.greenGradient)),
                "Спокойствие"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.greenGradient)),
                "Удовлетворенность"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.blueGradient)),
                "Депрессия"
            ),
            BallsItem((ContextCompat.getColor(requireContext(), R.color.blueGradient)), "Апатия"),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.greenGradient)),
                "Благодарность"
            ),
            BallsItem(
                (ContextCompat.getColor(requireContext(), R.color.greenGradient)),
                "Защищенность"
            ),
        )

        ballsAdapter.ballsList = bubbles
        binding?.ballsRecycler?.adapter = ballsAdapter

        val manager = GridLayoutManager(requireContext(), 4)

        binding?.ballsRecycler?.layoutManager = manager

        binding?.continueButton?.isEnabled = false

        binding?.backToFeelings?.setOnClickListener {
            view.findNavController()
                .navigate(R.id.chooseEmotionScreenToNavigationActivity)
        }
        binding?.continueButton?.setOnClickListener {
            view.findNavController().navigate(R.id.action_chooseEmotionScreen2_to_addNoteScreen)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
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
}