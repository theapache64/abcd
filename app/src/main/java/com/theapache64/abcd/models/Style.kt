package com.theapache64.abcd.models

open class Style(
    val code: String,
    val server: Server,
    val isSelected: Boolean = false
) {
    val imageUrl =  "http://${server.ip}/styles/$code.jpg"
}