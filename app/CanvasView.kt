package com.javacodegeeks.androidcanvasexample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView(internal var context: Context, attrs: AttributeSet) : View(context, attrs) {

    var width: Int = 0
    var height: Int = 0
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mPath: Path
    private val mPaint: Paint
    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()

    init {

        // we set a new Path
        mPath = Path()

        // and we set a new Paint with the desired attributes
        mPaint = Paint()
        mPaint.setAntiAlias(true)
        mPaint.setColor(Color.BLACK)
        mPaint.setStyle(Paint.Style.STROKE)
        mPaint.setStrokeJoin(Paint.Join.ROUND)
        mPaint.setStrokeWidth(4f)
    }

    // override onSizeChanged
    @Override
    protected fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    // override onDraw
    @Override
    protected fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint)
    }

    // when ACTION_DOWN start touch according to the x,y values
    private fun startTouch(x: Float, y: Float) {
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    // when ACTION_MOVE move touch according to the x,y values
    private fun moveTouch(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    fun clearCanvas() {
        mPath.reset()
        invalidate()
    }

    // when ACTION_UP stop touch
    private fun upTouch() {
        mPath.lineTo(mX, mY)
    }

    @Override
    fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.getX()
        val y = event.getY()

        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                startTouch(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                moveTouch(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                upTouch()
                invalidate()
            }
        }
        return true
    }

    companion object {
        private val TOLERANCE = 5f
    }
}