package com.example.firstlab.customView

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.FloatRange
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.firstlab.R

class EmotesFrequencyStripeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var widthPercent: Float = 1f
    private var colors: IntArray = intArrayOf(
        ContextCompat.getColor(context, R.color.weeklyEmotion),
        ContextCompat.getColor(context, R.color.white)
    )
    private val textView: TextView = TextView(context)

    init {
        textView.setTextAppearance(R.style.emotionFrequent)
        textView.setPadding(32, 16, 32, 16)
        addView(textView)
        updateBackground()
    }

    fun setPercent(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        widthPercent = percent
        requestLayout()
    }

    fun setCardText(text: String) {
        textView.text = text
    }

    fun setGradientColors(colorList: IntArray) {
        colors = colorList
        updateBackground()
    }

    private fun updateBackground() {
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, colors
        )
        gradientDrawable.cornerRadius = 80f
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT

        background = gradientDrawable
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val newWidth = (parentWidth * widthPercent).toInt()

        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY)
        super.onMeasure(newWidthMeasureSpec, heightMeasureSpec)
    }
}