package com.example.firstlab.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Visibility
import com.example.firstlab.R
import com.example.firstlab.components.BallsRecyclerAdapter
import com.example.firstlab.customView.BubbleView
import com.example.firstlab.databinding.EmotionsChooseBinding
import com.example.firstlab.models.BallsItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class ChooseEmotionScreen : AppCompatActivity() {
    private var binding: EmotionsChooseBinding? = null
    private lateinit var ballsAdapter: BallsRecyclerAdapter
    private var flexLayout: GridLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmotionsChooseBinding.inflate(layoutInflater)
        ballsAdapter =
            BallsRecyclerAdapter({ color, name -> showEmotionInfo(color, name) },
                { hideEmotionInfo() })
        setContentView(binding?.root)

        flexLayout = binding?.flexBox

        val bubbles = listOf(
            BallsItem(ContextCompat.getColor(this, R.color.redGradient), "Ярость"),
            BallsItem(ContextCompat.getColor(this, R.color.redGradient), "Напряжение"),
            BallsItem(ContextCompat.getColor(this, R.color.yellowGradient), "Возбуждение"),
            BallsItem((ContextCompat.getColor(this, R.color.yellowGradient)), "Восторг"),
            BallsItem(ContextCompat.getColor(this, R.color.redGradient), "Зависть"),
            BallsItem(ContextCompat.getColor(this, R.color.redGradient), "Беспокойство"),
            BallsItem((ContextCompat.getColor(this, R.color.yellowGradient)), "Уверенность"),
            BallsItem((ContextCompat.getColor(this, R.color.yellowGradient)), "Счастье"),
            BallsItem((ContextCompat.getColor(this, R.color.blueGradient)), "Выгопание"),
            BallsItem((ContextCompat.getColor(this, R.color.blueGradient)), "Усталость"),
            BallsItem((ContextCompat.getColor(this, R.color.greenGradient)), "Спокойствие"),
            BallsItem((ContextCompat.getColor(this, R.color.greenGradient)), "Удовлетворенность"),
            BallsItem((ContextCompat.getColor(this, R.color.blueGradient)), "Депрессия"),
            BallsItem((ContextCompat.getColor(this, R.color.blueGradient)), "Апатия"),
            BallsItem((ContextCompat.getColor(this, R.color.greenGradient)), "Благодарность"),
            BallsItem((ContextCompat.getColor(this, R.color.greenGradient)), "Защищенность"),
        )
        ballsAdapter.ballsList = bubbles
        binding?.ballsRecycler?.adapter = ballsAdapter

        val manager = GridLayoutManager(this, 4)

        binding?.ballsRecycler?.layoutManager = manager


        /*var selectedIndex = -1

        for ((index, bubble) in bubbles.withIndex()) {
            val bubbleView = BubbleView(this, bubble.color, bubble.name)

            bubbleView.setOnClickListener {
                if (selectedIndex != -1) {
                    val previousSelectedView = flexLayout?.getChildAt(selectedIndex) as? BubbleView
                    previousSelectedView?.revertSize(previousSelectedView)
                    previousSelectedView?.isSelected = false
                    hideEmotionInfo()
                }

                if (selectedIndex == index) {
                    selectedIndex = -1
                } else {
                    bubbleView.animateSizeChange(bubbleView)
                    bubbleView.isSelected = true
                    selectedIndex = index
                    showEmotionInfo(bubble.color, bubble.name)
                }
            }
            flexLayout?.addView(bubbleView)
        }*/



        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chooseEmotionsScreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding?.continueButton?.isEnabled = false

        binding?.backToFeelings?.setOnClickListener {
            finish()
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