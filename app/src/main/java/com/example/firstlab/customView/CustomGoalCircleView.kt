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
import com.example.firstlab.common.Constant.CIRCLE_ANIM_DURATION
import com.example.firstlab.common.Constant.START_ANGLE
import com.example.firstlab.common.Constant.STROKE_WIDTH
import com.example.firstlab.common.Constant.TOTAL_GOALS
import com.example.firstlab.domain.entity.EmotionType
import kotlin.math.cos
import kotlin.math.sin

class CustomGoalCircleView(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {


     var totalGoals = TOTAL_GOALS
        set(value) {
            field = if (value == 0) 1 else value
            checkAnimationState()
            invalidate()
        }

    var emotionsColors: List<EmotionType> = emptyList()
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
            strokeWidth = STROKE_WIDTH
        }
        canvas.drawCircle(cx, cy, radius, paint)
    }

    private fun drawRotatingArc(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        radius: Float
    ) {
        val mainColor = ContextCompat.getColor(context, R.color.circleSecondaryColor)
        val colors = intArrayOf(
            Color.TRANSPARENT,
            Color.argb(64, Color.red(mainColor), Color.green(mainColor), Color.blue(mainColor)),
            Color.argb(128, Color.red(mainColor), Color.green(mainColor), Color.blue(mainColor)),
            Color.argb(192, Color.red(mainColor), Color.green(mainColor), Color.blue(mainColor)),
            mainColor
        )
        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = createGradientForArc(angle, angle + 90f, centerX, centerY, radius, colors)
            style = Paint.Style.STROKE
            strokeWidth = STROKE_WIDTH
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
        if (emotionsColors.isEmpty()) return

        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        val anglePerGoal = 360f / totalGoals
        var startAngle = START_ANGLE

        val emotionCounts = emotionsColors.groupingBy { it }.eachCount()

        val totalSectors = emotionCounts.size
        var currentSector = 0

        emotionCounts.forEach { (type, count) ->
            val sectorAngle = anglePerGoal * count
            val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = createGradientForArc(
                    startAngle,
                    startAngle + sectorAngle,
                    centerX,
                    centerY,
                    radius,
                    when (type) {
                        EmotionType.BLUE -> intArrayOf(
                            ContextCompat.getColor(context, R.color.blueEmoteFirst),
                            ContextCompat.getColor(context, R.color.blueEmoteSecond)
                        )

                        EmotionType.RED -> intArrayOf(
                            ContextCompat.getColor(context, R.color.redEmoteFirst),
                            ContextCompat.getColor(context, R.color.redEmoteSecond)
                        )

                        EmotionType.GREEN -> intArrayOf(
                            ContextCompat.getColor(context, R.color.greenEmoteFirst),
                            ContextCompat.getColor(context, R.color.greenEmoteSecond)
                        )

                        EmotionType.YELLOW -> intArrayOf(
                            ContextCompat.getColor(context, R.color.yellowEmoteFirst),
                            ContextCompat.getColor(context, R.color.yellowEmoteSecond)
                        )
                    }
                )
                style = Paint.Style.STROKE
                strokeWidth = STROKE_WIDTH
                strokeCap = Paint.Cap.BUTT
            }

            canvas.drawArc(rect, startAngle, sectorAngle, false, arcPaint)

            if ((currentSector == totalSectors - 1 && emotionsColors.size != totalGoals) ||
                (currentSector == 0 && emotionsColors.size != totalGoals)
            ) {
                if (currentSector == totalSectors - 1) {
                    val roundPaint = Paint(arcPaint).apply {
                        strokeCap = Paint.Cap.ROUND
                    }
                    canvas.drawArc(rect, startAngle + sectorAngle - 1f, 1f, false, roundPaint)
                }

                if (currentSector == 0 && emotionsColors.size != totalGoals) {
                    val roundPaint = Paint(arcPaint).apply {
                        strokeCap = Paint.Cap.ROUND
                    }
                    canvas.drawArc(rect, startAngle - 1f, 1f, false, roundPaint)
                }
            }

            startAngle += sectorAngle
            currentSector++
        }
    }

    private fun createGradientForArc(
        startAngle: Float,
        endAngle: Float,
        centerX: Float,
        centerY: Float,
        radius: Float,
        colors: IntArray
    ): Shader {
        val startRad = Math.toRadians(startAngle.toDouble())
        val endRad = Math.toRadians(endAngle.toDouble())

        val startX = centerX + radius * cos(startRad).toFloat()
        val startY = centerY + radius * sin(startRad).toFloat()
        val endX = centerX + radius * cos(endRad).toFloat()
        val endY = centerY + radius * sin(endRad).toFloat()

        return LinearGradient(
            startX, startY, endX, endY,
            colors,
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
                duration = CIRCLE_ANIM_DURATION
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
