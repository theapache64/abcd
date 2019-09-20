package com.theapache64.abcd.models

import org.junit.Assert.assertEquals
import org.junit.Test

class StyleTest {


    @Test
    fun test() {
        val x = 5
        val y = 10
        val z = add(x, y)
        assertEquals(15, z)
    }

    fun add(x: Int, y: Int): Int {
        return x - y
    }
}