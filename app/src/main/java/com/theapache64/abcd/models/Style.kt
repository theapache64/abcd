package com.theapache64.abcd.models

open class Style(
    val code: String,
    val server: Server
) {
    val imageUrl = if (code == "random")
        "http://${server.ip}/random.png"
    else
        "http://${server.ip}/styles/$code.jpg"
}