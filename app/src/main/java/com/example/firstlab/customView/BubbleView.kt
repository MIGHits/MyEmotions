package com.example.firstlab.customView

import android.animation.ValueAnimator
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import com.example.firstlab.R

class BubbleView(
    context: Context,
    color: Int,
    private val text: String
) : CardView(context) {

    private val originalSize = dpToPx(112, context)
    private val expandedSize = dpToPx(152, context)
    private val textView: TextView
    private val originalMargin = dpToPx(4, context)

    init {
        layoutParams = LayoutParams(originalSize, originalSize).apply {
            marginStart = originalMargin
            marginEnd = originalMargin
            bottomMargin = originalMargin
        }

        radius = (originalSize / 2).toFloat()
        cardElevation = dpToPx(4, context).toFloat()
        setCardBackgroundColor(color)

        textView = TextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            gravity = Gravity.CENTER
            text = this@BubbleView.text
            setTextAppearance(R.style.emotionsRecyclerItem)
        }
        addView(textView)
    }

    fun animateSizeChange(
        view: BubbleView
    ) {
        val scale = expandedSize / originalSize.toFloat()
        val animator = ValueAnimator.ofFloat(1f, scale).apply {
            duration = 200
            addUpdateListener {
                val animatedScale = it.animatedValue as Float
                view.scaleX = animatedScale
                view.scaleY = animatedScale
                view.radius = (originalSize / 2f) * animatedScale

                val layoutParams = view.layoutParams as GridLayout.LayoutParams

                layoutParams.leftMargin = (originalMargin * animatedScale * 4).toInt()
                layoutParams.topMargin = (originalMargin * animatedScale * 4).toInt()
                layoutParams.rightMargin = (originalMargin * animatedScale * 4).toInt()
                layoutParams.bottomMargin = (originalMargin * animatedScale * 4).toInt()

                view.layoutParams = layoutParams

                view.requestLayout()
            }
            doOnEnd {

            }
        }
        animator.start()
    }

    fun revertSize(
        view: BubbleView
    ) {
        val scale = expandedSize / originalSize.toFloat()
        val animator = ValueAnimator.ofFloat(scale, 1f).apply {
            duration = 200
            addUpdateListener {
                val animatedScale = it.animatedValue as Float
                view.scaleX = animatedScale
                view.scaleY = animatedScale
                view.radius = (originalSize / 2f) * animatedScale

                val layoutParams = view.layoutParams as GridLayout.LayoutParams

                layoutParams.leftMargin = (originalMargin * animatedScale * 0.25).toInt()
                layoutParams.topMargin = (originalMargin * animatedScale * 0.25).toInt()
                layoutParams.rightMargin = (originalMargin * animatedScale * 0.25).toInt()
                layoutParams.bottomMargin = (originalMargin * animatedScale * 0.25).toInt()

                view.layoutParams = layoutParams

                view.requestLayout()
            }
        }
        animator.start()
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}
