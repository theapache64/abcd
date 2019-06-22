package com.theapache64.abcd.data.remote.receiveimage

import com.theapache64.abcd.models.Style
import java.io.File

class ReceiveImageRequest(
    val mapFile: File,
    val style: Style,
    var artStyle: Style
)