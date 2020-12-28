package com.photograph.paintcanvasview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View

class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint = Paint()
    private val path = Path()
    private val cornerPathEffect = CornerPathEffect(10F)

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.pathEffect = cornerPathEffect
        paint.strokeWidth = 60F
        paint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawPath(path, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN -> {
                path.rewind()
                path.moveTo(event.x, event.y)
                invalidate()
            }

            ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                invalidate()
            }

            ACTION_UP -> {
                path.lineTo(event.x, event.y)
                invalidate()
            }
        }

        return true
    }
}