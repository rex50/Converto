package com.rex50.converto.utils.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class DoubleExtensionsTest {

    @Test
    fun `using orZero for null double should return zero`() {
        val value: Double? = null
        assertEquals(0.0, value.orZero())
    }

}