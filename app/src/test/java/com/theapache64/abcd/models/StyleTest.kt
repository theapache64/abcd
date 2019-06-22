package com.theapache64.abcd.models

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class StyleTest {


    @Test
    fun givenStyleWhenApiCodeThenReplacedVersion() {
        val mockServer = mock(Server::class.java)
        val style = Style("Some Name", "art1", mockServer)
        assertEquals("1", style.apiCode)
    }

    @Test
    fun givenStyleWhenApiCodeThenSame() {
        val mockServer = mock(Server::class.java)
        val style = Style("Some Name", "mosaic", mockServer)
        assertEquals("mosaic", style.apiCode)
    }
}