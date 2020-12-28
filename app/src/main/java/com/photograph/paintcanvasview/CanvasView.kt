package com.photograph.paintcanvasview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint = Paint()
    private var touchX = 70F
    private var touchY = 100F
    private val cornerPathEffect = CornerPathEffect(10F)

    private var prevTouchX = 70F
    private var prevTouchY = 100F

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 線
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.pathEffect = cornerPathEffect
        paint.strokeWidth = 20F
        paint.color = Color.BLACK
        // (x1,y1,x2,y2,paint) 始点の座標(x1,y1), 終点の座標(x2,y2)
        canvas?.drawLine(touchX, touchY, prevTouchX, prevTouchY, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchX = event.x
                touchY = event.y
                invalidate()
            }
        }

        return true
    }
}