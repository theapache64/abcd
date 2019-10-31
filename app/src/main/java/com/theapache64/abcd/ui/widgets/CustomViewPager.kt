package com.theapache64.abcd.ui.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager


class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, _heightMeasureSpec: Int) {
        var heightMeasureSpec = _heightMeasureSpec

        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val h = child.measuredHeight
            if (h > height) height = h
        }

        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}