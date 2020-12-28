package com.photograph.paintcanvasview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager

class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val linePaint: Paint = Paint()
    private val circlePaint: Paint = Paint()
    private val path = Path()
    private var startPoint: PointF? = null
    private var endPoint: PointF? = null
    private var prevBitmap: Bitmap
    private var prevCanvas: Canvas
    private val strokeWidth = 30F

    init {
        val size = getSize()
        prevBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888)
        prevCanvas = Canvas(prevBitmap)

        linePaint.isAntiAlias = true
        linePaint.style = Paint.Style.STROKE
        linePaint.pathEffect = CornerPathEffect(30F)
        linePaint.strokeWidth = strokeWidth
        linePaint.color = Color.BLACK

        circlePaint.isAntiAlias = true
        circlePaint.style = Paint.Style.FILL
        circlePaint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(prevBitmap, 0F, 0F, null)
        canvas?.drawLine()
    }

    private fun Canvas.drawLine() {
        drawPath(path, linePaint)
        startPoint?.let {
            drawCircle(it.x, it.y, strokeWidth / 2, circlePaint)
        }
        endPoint?.let {
            drawCircle(it.x, it.y, strokeWidth / 2, circlePaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN -> {
                path.reset()
                path.moveTo(event.x, event.y)
                startPoint = PointF(event.x, event.y)
            }
            ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                endPoint = PointF(event.x, event.y)
            }
            ACTION_UP -> {
                path.lineTo(event.x, event.y)
                endPoint = PointF(event.x, event.y)
                prevCanvas.drawLine()
            }
        }
        invalidate()
        return true
    }

    @Suppress("DEPRECATION")
    private fun getSize(): Point {
        val windowManager = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val windowInsets = windowManager.currentWindowMetrics.windowInsets
            var insets: Insets = windowInsets.getInsets(WindowInsets.Type.navigationBars())
            windowInsets.displayCutout?.run {
                insets = Insets.max(
                    insets,
                    Insets.of(safeInsetLeft, safeInsetTop, safeInsetRight, safeInsetBottom)
                )
            }
            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom
            Point(
                windowManager.currentWindowMetrics.bounds.width() - insetsWidth,
                windowManager.currentWindowMetrics.bounds.height() - insetsHeight
            )
        } else {
            Point().apply {
                windowManager.defaultDisplay.getSize(this)
            }
        }
    }
}