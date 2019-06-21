package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.models.Brush
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrushRepository @Inject constructor() {

    companion object {
        private const val KEY_SEA = "Sea"
        private const val KEY_SKY = "Sky"
    }

    val brushes = listOf(
        Brush(KEY_SKY, "#9ceedd"),
        Brush("Tree", "#a8c832"),
        Brush("Cloud", "#696969"),
        Brush("Mountain", "#869664"),
        Brush("Grass", "#7bc800"),
        Brush(KEY_SEA, "#9ac6da"),
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

    /**
     * Returns sea in first and sky in second
     */
    fun getSeaAndSky(): Pair<Brush, Brush> {

        var sea: Brush? = null
        var sky: Brush? = null

        for (brush in brushes) {

            if (brush.name == KEY_SKY) {
                sky = brush
            } else if (brush.name == KEY_SEA) {
                sea = brush
            }

            if (sea != null && sky != null) {
                // sea and sky found, let's stop the loop
                break
            }
        }

        require(sea != null) { "Couldn't find sea from brushes" }
        require(sky != null) { "Couldn't find sky from brushes" }


        return Pair(sea, sky)
    }
}