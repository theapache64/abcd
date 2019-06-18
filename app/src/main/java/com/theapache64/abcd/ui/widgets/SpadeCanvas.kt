/**
 * CanvasView.java
 *
 * Copyright (c) 2014 Tomohiro IKEDA (Korilakkuma)
 * Released under the MIT license
 */

package com.theapache64.abcd.ui.widgets

import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.io.ByteArrayOutputStream
import java.util.*

// import android.util.Log;
// import android.widget.Toast;

/**
 * This class defines fields and methods for drawing.
 */
class SpadeCanvas : View {

    private var canvas: Canvas? = null
    private var bitmap: Bitmap? = null

    private val pathLists = ArrayList<Path>()
    private val paintLists = ArrayList<Paint>()

    // for Eraser
    /**
     * This method is getter for canvas background color
     *
     * @return
     */
    /**
     * This method is setter for canvas background color
     *
     * @param color
     */
    var baseColor = Color.WHITE

    // for Undo, Redo
    private var historyPointer = 0

    // Flags
    /**
     * This method is getter for mode.
     *
     * @return
     */
    /**
     * This method is setter for mode.
     *
     * @param mode
     */
    var mode = Mode.DRAW
    /**
     * This method is getter for drawer.
     *
     * @return
     */
    /**
     * This method is setter for drawer.
     *
     * @param drawer
     */
    var drawer = Drawer.PEN
    private var isDown = false

    // for Paint
    /**
     * This method is getter for stroke or fill.
     *
     * @return
     */
    /**
     * This method is setter for stroke or fill.
     *
     * @param style
     */
    var paintStyle: Paint.Style = Paint.Style.STROKE
    /**
     * This method is getter for stroke color.
     *
     * @return
     */
    /**
     * This method is setter for stroke color.
     *
     * @param color
     */
    var paintStrokeColor = Color.BLACK
    /**
     * This method is getter for fill color.
     * But, current Android API cannot set fill color (?).
     *
     * @return
     */
    /**
     * This method is setter for fill color.
     * But, current Android API cannot set fill color (?).
     *
     * @param color
     */
    var paintFillColor = Color.BLACK
    /**
     * This method is getter for stroke width.
     *
     * @return
     */
    /**
     * This method is setter for stroke width.
     *
     * @param width
     */
    var paintStrokeWidth = 3f
        set(width) = if (width >= 0) {
            field = width
        } else {
            field = 3f
        }
    /**
     * This method is getter for alpha.
     *
     * @return
     */
    /**
     * This method is setter for alpha.
     * The 1st argument must be between 0 and 255.
     *
     * @param opacity
     */
    var opacity = 255
        set(opacity) = if (opacity >= 0 && opacity <= 255) {
            field = opacity
        } else {
            field = 255
        }
    /**
     * This method is getter for amount of blur.
     *
     * @return
     */
    /**
     * This method is setter for amount of blur.
     * The 1st argument is greater than or equal to 0.0.
     *
     * @param blur
     */
    var blur = 0f
        set(blur) = if (blur >= 0) {
            field = blur
        } else {
            field = 0f
        }
    /**
     * This method is getter for line cap.
     *
     * @return
     */
    /**
     * This method is setter for line cap.
     *
     * @param cap
     */
    var lineCap: Paint.Cap = Paint.Cap.ROUND

    // for Text
    /**
     * This method is getter for drawn text.
     *
     * @return
     */
    /**
     * This method is setter for drawn text.
     *
     * @param text
     */
    var text = ""
    /**
     * This method is getter for font-family.
     *
     * @return
     */
    /**
     * This method is setter for font-family.
     *
     * @param face
     */
    var fontFamily = Typeface.DEFAULT
    /**
     * This method is getter for font size,
     *
     * @return
     */
    /**
     * This method is setter for font size.
     * The 1st argument is greater than or equal to 0.0.
     *
     * @param size
     */
    var fontSize = 32f
        set(size) = if (size >= 0f) {
            field = size
        } else {
            field = 32f
        }
    private val textAlign = Paint.Align.RIGHT  // fixed
    private var textPaint = Paint()
    private var textX = 0f
    private var textY = 0f

    // for Drawer
    private var startX = 0f
    private var startY = 0f
    private var controlX = 0f
    private var controlY = 0f

    /**
     * This method gets the instance of Path that pointer indicates.
     *
     * @return the instance of Path
     */
    private val currentPath: Path
        get() = this.pathLists[this.historyPointer - 1]

    /**
     * This method gets the bitmap as byte array.
     * Bitmap format is PNG, and quality is 100.
     *
     * @return This is returned as byte array of bitmap.
     */
    val bitmapAsByteArray: ByteArray
        get() = this.getBitmapAsByteArray(CompressFormat.PNG, 100)

    // Enumeration for Mode
    enum class Mode {
        DRAW,
        TEXT,
        ERASER
    }

    // Enumeration for Drawer
    enum class Drawer {
        PEN,
        LINE,
        RECTANGLE,
        CIRCLE,
        ELLIPSE,
        QUADRATIC_BEZIER,
        QUBIC_BEZIER
    }

    /**
     * Copy Constructor
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.setup(context)
    }

    /**
     * Copy Constructor
     *
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.setup(context)
    }

    /**
     * Copy Constructor
     *
     * @param context
     */
    constructor(context: Context) : super(context) {
        this.setup(context)
    }

    /**
     * Common initialization.
     *
     * @param context
     */
    private fun setup(context: Context) {

        this.pathLists.add(Path())
        this.paintLists.add(this.createPaint())
        this.historyPointer++

        this.textPaint.setARGB(0, 255, 255, 255)
    }

    /**
     * This method creates the instance of Paint.
     * In addition, this method sets styles for Paint.
     *
     * @return paint This is returned as the instance of Paint
     */
    private fun createPaint(): Paint {
        val paint = Paint()

        paint.isAntiAlias = true
        paint.style = this.paintStyle
        paint.strokeWidth = this.paintStrokeWidth
        paint.strokeCap = this.lineCap
        paint.strokeJoin = Paint.Join.MITER  // fixed

        // for Text
        if (this.mode == Mode.TEXT) {
            paint.typeface = this.fontFamily
            paint.textSize = this.fontSize
            paint.textAlign = this.textAlign
            paint.strokeWidth = 0f
        }

        if (this.mode == Mode.ERASER) {
            // Eraser
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            paint.setARGB(0, 0, 0, 0)

            // paint.setColor(this.baseColor);
            // paint.setShadowLayer(this.blur, 0F, 0F, this.baseColor);
        } else {
            // Otherwise
            paint.color = this.paintStrokeColor
            paint.setShadowLayer(this.blur, 0f, 0f, this.paintStrokeColor)
            paint.alpha = this.opacity
        }

        return paint
    }

    /**
     * This method initialize Path.
     * Namely, this method creates the instance of Path,
     * and moves current position.
     *
     * @param event This is argument of onTouchEvent method
     * @return path This is returned as the instance of Path
     */
    private fun createPath(event: MotionEvent): Path {
        val path = Path()

        // Save for ACTION_MOVE
        this.startX = event.x
        this.startY = event.y

        path.moveTo(this.startX, this.startY)

        return path
    }

    /**
     * This method updates the lists for the instance of Path and Paint.
     * "Undo" and "Redo" are enabled by this method.
     *
     * @param path the instance of Path
     * @param paint the instance of Paint
     */
    private fun updateHistory(path: Path) {
        if (this.historyPointer == this.pathLists.size) {
            this.pathLists.add(path)
            this.paintLists.add(this.createPaint())
            this.historyPointer++
        } else {
            // On the way of Undo or Redo
            this.pathLists[this.historyPointer] = path
            this.paintLists[this.historyPointer] = this.createPaint()
            this.historyPointer++

            var i = this.historyPointer
            val size = this.paintLists.size
            while (i < size) {
                this.pathLists.removeAt(this.historyPointer)
                this.paintLists.removeAt(this.historyPointer)
                i++
            }
        }
    }

    /**
     * This method draws text.
     *
     * @param canvas the instance of Canvas
     */
    private fun drawText(canvas: Canvas) {
        if (this.text.length <= 0) {
            return
        }

        if (this.mode == Mode.TEXT) {
            this.textX = this.startX
            this.textY = this.startY

            this.textPaint = this.createPaint()
        }

        val textX = this.textX
        val textY = this.textY

        val paintForMeasureText = Paint()

        // Line break automatically
        val textLength = paintForMeasureText.measureText(this.text)
        val lengthOfChar = textLength / this.text.length.toFloat()
        val restWidth = this.canvas!!.width - textX  // text-align : right
        val numChars =
            if (lengthOfChar <= 0) 1 else Math.floor((restWidth / lengthOfChar).toDouble()).toInt()  // The number of characters at 1 line
        val modNumChars = if (numChars < 1) 1 else numChars
        var y = textY

        var i = 0
        val len = this.text.length
        while (i < len) {
            var substring = ""

            if (i + modNumChars < len) {
                substring = this.text.substring(i, i + modNumChars)
            } else {
                substring = this.text.substring(i, len)
            }

            y += this.fontSize

            canvas.drawText(substring, textX, y, this.textPaint)
            i += modNumChars
        }
    }

    /**
     * This method defines processes on MotionEvent.ACTION_DOWN
     *
     * @param event This is argument of onTouchEvent method
     */
    private fun onActionDown(event: MotionEvent) {
        when (this.mode) {
            Mode.DRAW, Mode.ERASER -> if (this.drawer != Drawer.QUADRATIC_BEZIER && this.drawer != Drawer.QUBIC_BEZIER) {
                // Oherwise
                this.updateHistory(this.createPath(event))
                this.isDown = true
            } else {
                // Bezier
                if (this.startX == 0f && this.startY == 0f) {
                    // The 1st tap
                    this.updateHistory(this.createPath(event))
                } else {
                    // The 2nd tap
                    this.controlX = event.x
                    this.controlY = event.y

                    this.isDown = true
                }
            }
            Mode.TEXT -> {
                this.startX = event.x
                this.startY = event.y
            }
            else -> {
            }
        }
    }

    /**
     * This method defines processes on MotionEvent.ACTION_MOVE
     *
     * @param event This is argument of onTouchEvent method
     */
    private fun onActionMove(event: MotionEvent) {
        val x = event.x
        val y = event.y

        when (this.mode) {
            Mode.DRAW, Mode.ERASER ->

                if (this.drawer != Drawer.QUADRATIC_BEZIER && this.drawer != Drawer.QUBIC_BEZIER) {
                    if (!isDown) {
                        return
                    }

                    val path = this.currentPath

                    when (this.drawer) {
                        Drawer.PEN -> path.lineTo(x, y)
                        Drawer.LINE -> {
                            path.reset()
                            path.moveTo(this.startX, this.startY)
                            path.lineTo(x, y)
                        }
                        Drawer.RECTANGLE -> {
                            path.reset()
                            path.addRect(this.startX, this.startY, x, y, Path.Direction.CCW)
                        }
                        Drawer.CIRCLE -> {
                            val distanceX = Math.abs((this.startX - x).toDouble())
                            val distanceY = Math.abs((this.startX - y).toDouble())
                            val radius = Math.sqrt(Math.pow(distanceX, 2.0) + Math.pow(distanceY, 2.0))

                            path.reset()
                            path.addCircle(this.startX, this.startY, radius.toFloat(), Path.Direction.CCW)
                        }
                        Drawer.ELLIPSE -> {
                            val rect = RectF(this.startX, this.startY, x, y)

                            path.reset()
                            path.addOval(rect, Path.Direction.CCW)
                        }
                        else -> {
                        }
                    }
                } else {
                    if (!isDown) {
                        return
                    }

                    val path = this.currentPath

                    path.reset()
                    path.moveTo(this.startX, this.startY)
                    path.quadTo(this.controlX, this.controlY, x, y)
                }
            Mode.TEXT -> {
                this.startX = x
                this.startY = y
            }
            else -> {
            }
        }
    }

    /**
     * This method defines processes on MotionEvent.ACTION_DOWN
     *
     * @param event This is argument of onTouchEvent method
     */
    private fun onActionUp(event: MotionEvent) {
        if (isDown) {
            this.startX = 0f
            this.startY = 0f
            this.isDown = false
        }
    }

    /**
     * This method updates the instance of Canvas (View)
     *
     * @param canvas the new instance of Canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Before "drawPath"
        canvas.drawColor(this.baseColor)

        if (this.bitmap != null) {
            canvas.drawBitmap(this.bitmap!!, 0f, 0f, Paint())
        }

        for (i in 0 until this.historyPointer) {
            val path = this.pathLists[i]
            val paint = this.paintLists[i]

            canvas.drawPath(path, paint)
        }

        this.drawText(canvas)

        this.canvas = canvas
    }

    /**
     * This method set event listener for drawing.
     *
     * @param event the instance of MotionEvent
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> this.onActionDown(event)
            MotionEvent.ACTION_MOVE -> this.onActionMove(event)
            MotionEvent.ACTION_UP -> this.onActionUp(event)
            else -> {
            }
        }

        // Re draw
        this.invalidate()

        return true
    }

    /**
     * This method draws canvas again for Undo.
     *
     * @return If Undo is enabled, this is returned as true. Otherwise, this is returned as false.
     */
    fun undo(): Boolean {
        if (this.historyPointer > 1) {
            this.historyPointer--
            this.invalidate()

            return true
        } else {
            return false
        }
    }

    /**
     * This method draws canvas again for Redo.
     *
     * @return If Redo is enabled, this is returned as true. Otherwise, this is returned as false.
     */
    fun redo(): Boolean {
        if (this.historyPointer < this.pathLists.size) {
            this.historyPointer++
            this.invalidate()

            return true
        } else {
            return false
        }
    }

    /**
     * This method initializes canvas.
     *
     * @return
     */
    fun clear() {
        val path = Path()
        path.moveTo(0f, 0f)
        path.addRect(0f, 0f, 1000f, 1000f, Path.Direction.CCW)
        path.close()

        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL

        if (this.historyPointer == this.pathLists.size) {
            this.pathLists.add(path)
            this.paintLists.add(paint)
            this.historyPointer++
        } else {
            // On the way of Undo or Redo
            this.pathLists[this.historyPointer] = path
            this.paintLists[this.historyPointer] = paint
            this.historyPointer++

            var i = this.historyPointer
            val size = this.paintLists.size
            while (i < size) {
                this.pathLists.removeAt(this.historyPointer)
                this.paintLists.removeAt(this.historyPointer)
                i++
            }
        }

        this.text = ""

        // Clear
        this.invalidate()
    }

    /**
     * This method gets current canvas as bitmap.
     *
     * @return This is returned as bitmap.
     */
    fun getBitmap(): Bitmap {
        this.isDrawingCacheEnabled = false
        this.isDrawingCacheEnabled = true

        return Bitmap.createBitmap(this.drawingCache)
    }

    /**
     * This method gets current canvas as scaled bitmap.
     *
     * @return This is returned as scaled bitmap.
     */
    fun getScaleBitmap(w: Int, h: Int): Bitmap {
        this.isDrawingCacheEnabled = false
        this.isDrawingCacheEnabled = true

        return Bitmap.createScaledBitmap(this.drawingCache, w, h, true)
    }

    /**
     * This method draws the designated bitmap to canvas.
     *
     * @param bitmap
     */
    fun drawBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        this.invalidate()
    }

    /**
     * This method draws the designated byte array of bitmap to canvas.
     *
     * @param byteArray This is returned as byte array of bitmap.
     */
    fun drawBitmap(byteArray: ByteArray) {
        this.drawBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
    }

    /**
     * This method gets the bitmap as byte array.
     *
     * @param format
     * @param quality
     * @return This is returned as byte array of bitmap.
     */
    fun getBitmapAsByteArray(format: CompressFormat, quality: Int): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.getBitmap().compress(format, quality, byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }

    companion object {

        /**
         * This static method gets the designated bitmap as byte array.
         *
         * @param bitmap
         * @param format
         * @param quality
         * @return This is returned as byte array of bitmap.
         */
        fun getBitmapAsByteArray(bitmap: Bitmap, format: CompressFormat, quality: Int): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(format, quality, byteArrayOutputStream)

            return byteArrayOutputStream.toByteArray()
        }
    }

}