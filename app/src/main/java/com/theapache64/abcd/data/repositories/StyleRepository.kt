package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.models.Server
import com.theapache64.abcd.models.Style
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StyleRepository @Inject constructor(
    server: Server
) {

    companion object {
        private const val CODE_NONE = "none"
    }

    val styles = listOf(
        Style("Random", "random", server),
        Style("Nightfall", "0", server),
        Style("Highway", "1", server),
        Style("Loch", "2", server),
        Style("Dusk", "3", server),
        Style("Twilight", "4", server),
        Style("Dawn", "5", server),
        Style("Marine", "6", server),
        Style("Lagoon", "7", server),
        Style("Reflection", "8", server),
        Style("Dark", "9", server),
        Style("Eve", "10", server)
    )

    val artStyles = listOf(
        Style("None", CODE_NONE, server),
        Style("Candy", "candy", server),
        Style("Mosaic", "mosaic", server),
        Style("Rain Princess", "rain-princess-cropped", server),
        Style("Udnie", "udnie", server),
        Style("Wassily", "art0", server),
        Style("Turcato", "art1", server),
        Style("Asheville", "art2", server),
        Style("Mathias", "art3", server),
        Style("WWH", "art4", server),
        Style("La Muse", "art5", server),
        Style("Self", "art6", server),
        Style("En Campo", "art7", server)
    )

    fun getNoArtStyle(): Style {
        var noneArtStyle: Style? = null
        for (style in artStyles) {
            if (style.code == CODE_NONE) {
                noneArtStyle = style
                break
            }
        }
        require(noneArtStyle != null) { "Couldn't find none artistic style" }
        return noneArtStyle
    }

}