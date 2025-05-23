package com.example.firstlab.customView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.firstlab.R
import com.example.firstlab.common.Constant.PADDING_SMALL
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.presentation.models.MoodCategory

class MoodColumnView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12.dpToPx()
        textAlign = Paint.Align.CENTER
        typeface = ResourcesCompat.getFont(context, R.font.vela_sans_bold)
    }

    var emotionsList: List<MoodCategory> =
        listOf()
    var colors = intArrayOf()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var top = 0f
        var currentHeight = 0f

        if (emotionsList.isEmpty()) {
            paint.apply {
                color = ContextCompat.getColor(context, R.color.navbarBackground)
            }

            canvas.drawRoundRect(
                0f,
                top,
                width.toFloat(),
                height.toFloat(),
                PADDING_SMALL.dpToPx(),
                PADDING_SMALL.dpToPx(),
                paint
            )
        } else {
            emotionsList.forEach { index ->
                currentHeight += height.toFloat() * index.category.first
                colors = getGradientColors(index.category.second)
                paint.apply {
                    shader = LinearGradient(
                        0f,
                        top,
                        width.toFloat(),
                        currentHeight,
                        colors,
                        null,
                        Shader.TileMode.CLAMP
                    )
                }

                canvas.drawRoundRect(
                    0f,
                    top,
                    width.toFloat(),
                    currentHeight,
                    PADDING_SMALL.dpToPx(),
                    PADDING_SMALL.dpToPx(),
                    paint
                )
                canvas.drawText(
                    "${(index.category.first * 100).toInt()}%",
                    width / 2f,
                    (currentHeight - (currentHeight - top) / 2f),
                    textPaint
                )
                top = currentHeight + 4.dpToPx()
            }
        }
    }

    fun setEmotionList(emotions: List<MoodCategory>) {
        emotionsList = emotions
    }

    private fun getGradientColors(type: EmotionType): IntArray {
        return when (type) {
            EmotionType.BLUE ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.blueEmoteFirst),
                    ContextCompat.getColor(context, R.color.blueEmoteSecond)
                )

            EmotionType.GREEN ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.greenEmoteFirst),
                    ContextCompat.getColor(context, R.color.greenEmoteSecond)
                )

            EmotionType.RED ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.redEmoteFirst),
                    ContextCompat.getColor(context, R.color.redEmoteSecond),
                )

            EmotionType.YELLOW ->
                intArrayOf(
                    ContextCompat.getColor(context, R.color.yellowEmoteFirst),
                    ContextCompat.getColor(context, R.color.yellowEmoteSecond)
                )

        }
    }

    private fun Int.dpToPx(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, toFloat(), Resources.getSystem().displayMetrics
        )
    }
}