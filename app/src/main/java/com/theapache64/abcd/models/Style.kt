package com.theapache64.abcd.models

import com.theapache64.abcd.data.repositories.StyleRepository
import java.io.Serializable

open class Style(
    val name: String,
    val code: String,
    val server: Server,
    val apiCode : String
) : Serializable {


    val imageUrl = if (code == StyleRepository.CODE_RANDOM)
    // Random image url
        "https://i.imgur.com/gin0rqw.png"
    else
        "http://${server.ip}/styles/$code.jpg"
}