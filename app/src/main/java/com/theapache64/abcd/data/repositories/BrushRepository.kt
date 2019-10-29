package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.R
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.models.BrushCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrushRepository @Inject constructor() {

    companion object {
        private const val KEY_SEA = "Sea"
        private const val KEY_SKY = "Sky"
        private const val KEY_MOUNTAIN = "Mountain"
    }

    val brushCategories = listOf(

        BrushCategory(
            "Building",
            R.drawable.building,
            listOf(
                Brush("Bridge", "#5e5bc5"),
                Brush("Fence", "#706419"),
                Brush("House", "#7f4502"),
                Brush("Platform", "#8f2a91"),
                Brush("Roof", "#9600b1"),
                Brush("Wall-brick", "#aad16a"),
                Brush("Wall-stone", "#ae2974"),
                Brush("Wall-wood", "#b0c1c3")
            )
        ),

        BrushCategory(
            "Ground",
            R.drawable.ground,
            listOf(
                Brush("Dirt", "#6e6e28"),
                Brush("Gravel", "#7c32c8"),
                Brush("Ground-other", "#7d3054"),
                Brush("Mud", "#87716f"),
                Brush("Pavement", "#8b3027"),
                Brush("Road", "#946e28"),
                Brush("Sand", "#999900")
            )
        ),

        BrushCategory(
            "Landscape",
            R.drawable.landscape,
            listOf(
                Brush("Clouds", "#696969"),
                Brush("Fog", "#77ba1d"),
                Brush("Hill", "#7ec864"),
                Brush(KEY_MOUNTAIN, "#869664"),
                Brush("River", "#9364c8"),
                Brush("Rock", "#956432"),
                Brush(KEY_SEA, "#9ac6da"),
                Brush(KEY_SKY, "#9ceedd"),
                Brush("Snow", "#9e9eaa"),
                Brush("Stone", "#a1a164"),
                Brush("Water", "#b1c8ff")
            )
        ),

        BrushCategory(
            "Plant",
            R.drawable.plants,
            listOf(
                Brush("Bush", "#606e32"),
                Brush("Flower", "#760000"),
                Brush("Grass", "#7bc800"),
                Brush("Straw", "#a2a3eb"),
                Brush("Tree", "#a8c832"),
                Brush("Wood", "#b57b00")
            )
        )

    )

    /**
     * Returns sea in first and sky in second
     */
    fun getSeaSkyAndMountain(): Triple<Brush, Brush, Brush> {

        var sea: Brush? = null
        var sky: Brush? = null
        var mountain: Brush? = null

        var isBreakLoop = false
        for (brushCat in brushCategories) {

            for (brush in brushCat.brushes) {
                // sea and sky found, let's stop the loop
                when (brush.name) {
                    KEY_SKY -> sky = brush
                    KEY_SEA -> sea = brush
                    KEY_MOUNTAIN -> mountain = brush
                }

                if (sea != null && sky != null && mountain != null) {
                    // sea, sky and mountain got found, let's stop the loop
                    isBreakLoop = true
                    break
                }
            }

            if (isBreakLoop) {
                break
            }
        }

        require(sea != null) { "Couldn't find sea from brushes" }
        require(sky != null) { "Couldn't find sky from brushes" }
        require(mountain != null) { "Couldn't find mountain from brushes" }


        return Triple(sea, sky, mountain)
    }
}