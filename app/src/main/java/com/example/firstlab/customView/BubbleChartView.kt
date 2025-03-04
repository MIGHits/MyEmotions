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
import com.example.firstlab.models.EmotionType
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class BubbleChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val colors = listOf(
        intArrayOf(R.color.greenEmoteFirst, R.color.greenEmoteSecond),
        intArrayOf(R.color.yellowEmoteFirst, R.color.yellowEmoteSecond),
        intArrayOf(R.color.blueEmoteFirst, R.color.blueEmoteSecond),
        intArrayOf(R.color.redEmoteFirst, R.color.redEmoteSecond)
    )

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = dpToPx(20)
        textAlign = Paint.Align.CENTER
        typeface = ResourcesCompat.getFont(context, R.font.vela_sans_bold)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var gradient: LinearGradient? = null

    var percentages: List<Pair<Float, EmotionType>> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (percentages.isEmpty()) return

        val sorted = percentages
            .mapIndexed { index, percent -> Pair(percent, index) }
            .sortedByDescending { it.first.first }

        var minDiffIndex = 0
        var minDiff = Float.MAX_VALUE
        for (i in 0 until sorted.size - 1) {
            val diff = abs(sorted[i].first.first - sorted[i + 1].first.first)
            if (diff < minDiff) {
                minDiff = diff
                minDiffIndex = i
            }
        }

        val arranged = mutableListOf<Pair<Pair<Float, EmotionType>, Int>>()
        arranged.add(sorted[minDiffIndex])
        arranged.add(sorted[minDiffIndex + 1])

        sorted.forEachIndexed { index, item ->
            if (index != minDiffIndex && index != minDiffIndex + 1) {
                arranged.add(item)
            }
        }

        val maxRadius = width / 2f
        val minRadius = dpToPx(20)

        val positions = listOf(
            Pair(width, 0),
            Pair(0, height),
            Pair(0, 0),
            Pair(width, height),
        )

        arranged.forEachIndexed { index, (percentage) ->
            val rawRadius = width * (percentage.first / 100f) * 0.75f
            val radius = max(minRadius, min(maxRadius, rawRadius))

            val (cornerX, cornerY) = positions[index]
            val x = if (cornerX == 0) radius else width - radius
            val y = if (cornerY == 0) radius else height - radius

            val gradientColors = when (percentage.second) {
                EmotionType.BLUE -> colors[2]
                EmotionType.RED -> colors[3]
                EmotionType.YELLOW -> colors[1]
                EmotionType.GREEN -> colors[0]
            }.map { ContextCompat.getColor(context, it) }.toIntArray()

            gradient = LinearGradient(
                x - radius, y - radius,
                x + radius, y + radius,
                gradientColors,
                null,
                Shader.TileMode.CLAMP
            )
            paint.shader = gradient

            if (percentage.first != 0f) {
                canvas.drawCircle(x, y, radius, paint)
                canvas.drawText("${percentage.first.toInt()}%", x, y + dpToPx(6), textPaint)
            }
        }
    }

    private fun dpToPx(dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), Resources.getSystem().displayMetrics
        )
    }
}



