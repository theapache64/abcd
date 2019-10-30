package com.theapache64.abcd.models

import androidx.annotation.DrawableRes

class BrushCategory(
    val name: String,
    @DrawableRes val image: Int,
    val brushes: List<Brush>
) {
    override fun toString(): String {
        return name
    }
}