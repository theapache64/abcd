package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.models.Server
import com.theapache64.abcd.models.Style
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StyleRepository @Inject constructor(
    server: Server
) {
    val styles = listOf(
        Style("random", server),
        Style("0", server),
        Style("1", server),
        Style("2", server),
        Style("3", server),
        Style("4", server),
        Style("5", server),
        Style("6", server),
        Style("7", server),
        Style("8", server),
        Style("9", server),
        Style("10", server)
    )

    private val artisticStyles = listOf(
        Style("candy", server),
        Style("mosaic", server),
        Style("rain-princess-cropped", server),
        Style("udnie", server),
        Style("art0", server),
        Style("art1", server),
        Style("art2", server),
        Style("art3", server),
        Style("art4", server),
        Style("art5", server),
        Style("art6", server),
        Style("art7", server)
    )
}