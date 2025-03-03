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

    var goalsDone = 0
        set(value) {
            field = value
            checkAnimationState()
            invalidate()
        }

    var totalGoals = 6
        set(value) {
            field = if (value == 0) 1 else value
            checkAnimationState()
            invalidate()
        }

    var emotionsColors: List<Int> = listOf(

    )
        set(value) {
            field = value
            checkAnimationState()
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val primaryColor = ContextCompat.getColor(context, R.color.circlePrimaryColor)
    private var angle = 0f
    private var animator: ValueAnimator? = null

    init {
        checkAnimationState()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2
        val centerY = height / 2
        val radius = width / 2.2f

        drawGoalsCircle(canvas, centerX, centerY, radius, primaryColor)

        if (emotionsColors.isEmpty()) {
            drawRotatingArc(canvas, centerX, centerY, radius)
        } else {
            drawEmotionSectors(canvas, centerX, centerY, radius)
        }
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

    private fun drawRotatingArc(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        radius: Float
    ) {
        val color = ContextCompat.getColor(context, R.color.circleSecondaryColor)
        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = createGradientForArc(angle, angle + 90f, centerX, centerY, radius, color)
            style = Paint.Style.STROKE
            strokeWidth = 60f
            strokeCap = Paint.Cap.ROUND
        }

        canvas.drawArc(rect, angle, 90f, false, arcPaint)
    }

    private fun drawEmotionSectors(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        radius: Float
    ) {
        if (goalsDone == 0 || emotionsColors.isEmpty()) return

        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        val anglePerGoal = 360f / totalGoals
        var startAngle = -90f

        val emotionCounts = emotionsColors.take(goalsDone).groupingBy { it }.eachCount()

        emotionCounts.forEach { (color, count) ->
            val sectorAngle = anglePerGoal * count

            val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = createGradientForArc(
                    startAngle,
                    startAngle + sectorAngle,
                    centerX,
                    centerY,
                    radius,
                    color
                )
                style = Paint.Style.STROKE
                strokeWidth = 60f
                strokeCap = Paint.Cap.BUTT
            }

            canvas.drawArc(rect, startAngle, sectorAngle, false, arcPaint)
            startAngle += sectorAngle
        }
    }

    private fun createGradientForArc(
        startAngle: Float,
        endAngle: Float,
        centerX: Float,
        centerY: Float,
        radius: Float,
        color: Int
    ): Shader {
        val startRad = Math.toRadians(startAngle.toDouble())
        val endRad = Math.toRadians(endAngle.toDouble())

        val startX = centerX + radius * cos(startRad).toFloat()
        val startY = centerY + radius * sin(startRad).toFloat()
        val endX = centerX + radius * cos(endRad).toFloat()
        val endY = centerY + radius * sin(endRad).toFloat()

        return LinearGradient(
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
    }

    private fun checkAnimationState() {
        if (emotionsColors.isEmpty()) {
            startAnimation()
        } else {
            stopAnimation()
        }
    }

    private fun startAnimation() {
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 360f).apply {
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

    private fun stopAnimation() {
        animator?.cancel()
        animator = null
        angle = 0f
    }
}
