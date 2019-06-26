package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.R
import com.theapache64.abcd.models.Style
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StyleRepository @Inject constructor(
) {

    companion object {
        const val CODE_NONE = "none"
        const val CODE_RANDOM = "random"
    }

    val styles = listOf(
        Style("Random", R.drawable.style_random, CODE_RANDOM),
        Style("Nightfall", R.drawable.style_0, "0"),
        Style("Highway", R.drawable.style_1, "1"),
        Style("Loch", R.drawable.style_2, "2"),
        Style("Dusk", R.drawable.style_3, "3"),
        Style("Twilight", R.drawable.style_4, "4"),
        Style("Dawn", R.drawable.style_5, "5"),
        Style("Marine", R.drawable.style_6, "6"),
        Style("Lagoon", R.drawable.style_7, "7"),
        Style("Reflection", R.drawable.style_8, "8"),
        Style("Dark", R.drawable.style_9, "9"),
        Style("Eve", R.drawable.style_10, "10")
    )

    val artStyles = listOf(
        Style("None", R.drawable.none, CODE_NONE),
        Style("Candy", R.drawable.art_candy, "candy"),
        Style("Mosaic", R.drawable.art_mosaic, "mosaic"),
        Style("Rain Princess", R.drawable.art_rain_princess_cropped, "rain"),
        Style("Udnie", R.drawable.art_udnie, "udnie"),
        Style("Wassily", R.drawable.art_0, "0"),
        Style("Turcato", R.drawable.art_1, "1"),
        Style("Asheville", R.drawable.art_2, "2"),
        Style("Mathias", R.drawable.art_3, "3"),
        Style("WWH", R.drawable.art_4, "4"),
        Style("La Muse", R.drawable.art_5, "5"),
        Style("Self", R.drawable.art_6, "6"),
        Style("En Campo", R.drawable.art_7, "7")
    )

    fun getNoArtStyle(): Style {
        var noneArtStyle: Style? = null
        for (style in artStyles) {
            if (style.apiCode == CODE_NONE) {
                noneArtStyle = style
                break
            }
        }
        require(noneArtStyle != null) { "Couldn't find none artistic style" }
        return noneArtStyle
    }

}