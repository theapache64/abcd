package com.theapache64.abcd.models

import com.theapache64.abcd.data.repositories.StyleRepository
import java.io.Serializable

open class Style(
    val name: String,
    val code: String,
    val server: Server
) : Serializable {

    companion object {
        val ANY_NUM = Regex("[0-9]")
        val ALL_LETTERS = Regex("[A-Za-z]+")
    }

    val imageUrl = if (code == StyleRepository.CODE_RANDOM)
    // Random image url
        "https://i.imgur.com/6ckpkfe.png"
    else
        "http://${server.ip}/styles/$code.jpg"

    val apiCode = if (code.contains(ANY_NUM)) {
        code.replace(ALL_LETTERS, "")
    } else {
        code
    }
}