package com.theapache64.abcd.models

import org.junit.Assert.assertEquals
import org.junit.Test

class StyleTest {

    @Test
    fun givenStyleWhenStyleImageUrlThen0jpg() {
        val server = Server("Server 1", "1.1.1.1")
        val style = Style("0", server)
        assertEquals(style.imageUrl, "http://${server.ip}/styles/0.jpg")
    }
}