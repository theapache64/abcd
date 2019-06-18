package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.models.Brush
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrushRepository @Inject constructor() {

    private val brushes = listOf(
        Brush("Sky", "#9ceedd"),
        Brush("Tree", "#a8c832"),
        Brush("Cloud", "#696969"),
        Brush("Mountain", "#869664"),
        Brush("Grass", "#7bc800"),
        Brush("Sea", "#9ac6da"),
        Brush("River", "#9364c8"),
        Brush("Rock", "#956432"),
        Brush("Plant", "#8de61e"),
        Brush("Sand", "#999900"),
        Brush("Snow", "#9e9eaa"),
        Brush("Water", "#b1c8ff"),
        Brush("Hill", "#7ec864"),
        Brush("Dirt", "#6e6e28"),
        Brush("Road", "#946e28"),
        Brush("Flower", "#760000"),
        Brush("Stone", "#a1a164"),
        Brush("Bush", "#606e32"),
        Brush("Wood", "#b57b00"),
        Brush("Gravel", "#7c32c8")
    )
}