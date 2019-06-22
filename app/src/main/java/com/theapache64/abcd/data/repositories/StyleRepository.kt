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
        const val CODE_RANDOM = "random"
    }

    val styles = listOf(
        Style("Random", CODE_RANDOM, server, "random"),
        Style("Nightfall", "0", server, "0"),
        Style("Highway", "1", server, "1"),
        Style("Loch", "2", server, "2"),
        Style("Dusk", "3", server, "3"),
        Style("Twilight", "4", server, "4"),
        Style("Dawn", "5", server, "5"),
        Style("Marine", "6", server, "6"),
        Style("Lagoon", "7", server, "7"),
        Style("Reflection", "8", server, "8"),
        Style("Dark", "9", server, "9"),
        Style("Eve", "10", server, "10")
    )

    val artStyles = listOf(
        Style("None", CODE_NONE, server, "none"),
        Style("Candy", "candy", server, "candy"),
        Style("Mosaic", "mosaic", server, "mosaic"),
        Style("Rain Princess", "rain-princess-cropped", server, "rain"),
        Style("Udnie", "udnie", server, "udnie"),
        Style("Wassily", "art0", server, "0"),
        Style("Turcato", "art1", server, "1"),
        Style("Asheville", "art2", server, "2"),
        Style("Mathias", "art3", server, "3"),
        Style("WWH", "art4", server, "4"),
        Style("La Muse", "art5", server, "5"),
        Style("Self", "art6", server, "6"),
        Style("En Campo", "art7", server, "7")
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