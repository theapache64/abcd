package com.theapache64.abcd.utils

/**
 * All brush related calculations are done here
 */
object BrushUtils {

    fun toProgress(currentBrushSize: Float): Int {
        return (currentBrushSize / BRUSH_SIZE_FACTOR).toInt()
    }

    fun toBrushSize(progress: Int): Float {
        return progress * BRUSH_SIZE_FACTOR
    }

    fun getDefaultBrushSize(): Float {
        return toBrushSize(DEFAULT_BRUSH_PROGRESS)
    }


    private const val DEFAULT_BRUSH_PROGRESS = 25
    private const val BRUSH_SIZE_FACTOR = 3f


}