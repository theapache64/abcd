package com.theapache64.abcd.models

import java.io.Serializable

open class Style(
    val name: String,
    val code: String,
    val server: Server
) : Serializable {
    val imageUrl = if (code == "random")
        "http://${server.ip}/random.png"
    else
        "http://${server.ip}/styles/$code.jpg"
}