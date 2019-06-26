package com.theapache64.abcd.models

import androidx.annotation.DrawableRes
import java.io.Serializable

open class Style(
    val name: String,
    @DrawableRes val image: Int,
    val apiCode: String
) : Serializable