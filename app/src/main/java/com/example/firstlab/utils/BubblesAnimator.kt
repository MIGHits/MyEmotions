package com.example.firstlab.utils

import android.animation.ValueAnimator
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.view.marginStart
import com.example.firstlab.customView.BubbleView

class BubblesAnimator {
    companion object {



        private fun dpToPx(dp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }
    }
}