package com.example.firstlab.customView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.firstlab.R
import com.example.firstlab.common.Constant.GRADIENT_ANIM_DURATION
import kotlin.math.cos
import kotlin.math.sin

class CustomGradientView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    init {
        startAnimation()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var angle = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = height * 0.5f

        drawAnimatedCircle(
            canvas,
            width / 2,
            height / 2,
            radius,
            height * 0.7f,
            ContextCompat.getColor(context, R.color.gradientGreen),
            170,
            0
        )
        drawAnimatedCircle(
            canvas,
            width / 2,
            height / 2,
            radius,
            height * 0.7f,
            ContextCompat.getColor(context, R.color.gradientSkyBlue),
            170,
            90
        )
        drawAnimatedCircle(
            canvas,
            width / 2,
            height / 2,
            radius,
            height * 0.7f,
            ContextCompat.getColor(context, R.color.gradientDarkYellow),
            170,
            180
        )
        drawAnimatedCircle(
            canvas,
            width / 2,
            height / 2,
            radius,
            height * 0.7f,
            ContextCompat.getColor(context, R.color.gradientRed),
            170,
            270
        )
    }

    private fun drawAnimatedCircle(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        orbitRadius: Float,
        circleRadius: Float,
        color: Int,
        alpha: Int,
        offsetAngle: Int
    ) {
        val animatedAngle = Math.toRadians((angle + offsetAngle).toDouble())
        val cx = centerX + (orbitRadius * cos(animatedAngle)).toFloat()
        val cy = centerY + (orbitRadius * sin(animatedAngle)).toFloat()

        drawGradientCircle(canvas, cx, cy, circleRadius, color, alpha)
    }

    private fun drawGradientCircle(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radius: Float,
        color: Int,
        alpha: Int
    ) {
        val transparentColor =
            Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))

        paint.shader = RadialGradient(
            cx,
            cy,
            radius,
            transparentColor,
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
        canvas.drawCircle(cx, cy, radius, paint)
    }

    private fun startAnimation() {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration =  GRADIENT_ANIM_DURATION
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