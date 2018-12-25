package com.example.matek3022.nirs

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {
        val testArray = floatArrayOf(
            50f, 68f, 10f, 0f, 8f, 7f, 1f, 0f,
            82f, 63f, 19f, 0f, 2f, 0f, 0f, 0f,
            31f, 4f, 0f, 1f, 0f, 0f, 0f, 0f,
            0f, 0f, 1f, 1f, 0f, 0f, 0f, 0f,
            1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f, 0f, 0f, 0f, 38f,
            0f, 1f, 0f, 0f, 22f, 30f, 3f, 14f,
            0f, 0f, 6f, 54f, 78f, 32f, 0f, 0f
        )
        Dct.forwardDCT8x8(testArray)
        testArray.devideArray(64)
        Dct.inverseDCT8x8(testArray)
        toQuantiz(testArray)
    }
}
