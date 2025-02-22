package com.example.firstlab.customView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.firstlab.R
import kotlin.math.cos
import kotlin.math.sin

class CustomGoalCircleView(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {

    init {
        startAnimation()
    }

    private var emotionsColors = listOf(
        ContextCompat.getColor(context, R.color.circleSecondaryColor)
    )
    private var goalsDone = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var angle = 0f
    private val primaryColor = ContextCompat.getColor(context, R.color.circlePrimaryColor)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        drawGoalsCircle(
            canvas,
            (width / 2),
            (height / 2),
            width / 2.2f,
            primaryColor
        )

        drawAnimatedArc(canvas, (width / 2), (height / 2), width / 2.2f)
    }

    fun setCircleColors(gradientColors: List<Int>) {
        emotionsColors = gradientColors
        goalsDone = gradientColors.size / 2
    }

    private fun drawGoalsCircle(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radius: Float,
        circleColor: Int
    ) {
        paint.apply {
            color = circleColor
            style = Paint.Style.STROKE
            strokeWidth = 60f
        }
        canvas.drawCircle(cx, cy, radius, paint)
    }

    private fun drawAnimatedArc(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        orbitRadius: Float,
        color: Int = ContextCompat.getColor(context, R.color.circleSecondaryColor)
    ) {
        val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            val startAngleRad = Math.toRadians(angle.toDouble())
            val endAngleRad = Math.toRadians((angle + 90f).toDouble())

            val startX = centerX + orbitRadius * cos(startAngleRad).toFloat()
            val startY = centerY + orbitRadius * sin(startAngleRad).toFloat()
            val endX = centerX + orbitRadius * cos(endAngleRad).toFloat()
            val endY = centerY + orbitRadius * sin(endAngleRad).toFloat()

            shader = LinearGradient(
                startX, startY, endX, endY,
                intArrayOf(
                    Color.TRANSPARENT,
                    Color.argb(64, Color.red(color), Color.green(color), Color.blue(color)),
                    Color.argb(128, Color.red(color), Color.green(color), Color.blue(color)),
                    Color.argb(192, Color.red(color), Color.green(color), Color.blue(color)),
                    color
                ),
                null,
                Shader.TileMode.CLAMP
            )
            style = Paint.Style.STROKE
            strokeWidth = 60f
            strokeCap = Paint.Cap.ROUND
        }

        val rect = RectF(
            centerX - orbitRadius,
            centerY - orbitRadius,
            centerX + orbitRadius,
            centerY + orbitRadius
        )

        canvas.drawArc(rect, angle, 90f, false, arcPaint)
    }

    private fun startAnimation() {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 5000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                angle = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }
}