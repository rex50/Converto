package com.rex50.converto.utils.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class DoubleExtensionsTest {

    @Test
    fun `using orZero for null double should return zero`() {
        val value: Double? = null
        assertEquals(0.0, value.orZero())
    }

    @Test
    fun `using orZero for non null double should return the same value`() {
        val value: Double? = 5.5
        assertEquals(5.5, value.orZero())
    }

    @Test
    fun `using orZero for zero double should return zero`() {
        val value: Double? = 0.0
        assertEquals(0.0, value.orZero())
    }

    @Test
    fun `using orZero for negative double should return the same negative value`() {
        val value: Double? = -3.14
        assertEquals(-3.14, value.orZero())
    }

    @Test
    fun `using orZero for positive double should return the same positive value`() {
        val value: Double? = 42.0
        assertEquals(42.0, value.orZero())
    }

    @Test
    fun `using orZero for very small positive value should return the same value`() {
        val value: Double? = Double.MIN_VALUE
        assertEquals(Double.MIN_VALUE, value.orZero())
    }

    @Test
    fun `using orZero for very large positive value should return the same value`() {
        val value: Double? = Double.MAX_VALUE
        assertEquals(Double.MAX_VALUE, value.orZero())
    }

    @Test
    fun `using orZero for negative infinity should return negative infinity`() {
        val value: Double? = Double.NEGATIVE_INFINITY
        assertEquals(Double.NEGATIVE_INFINITY, value.orZero())
    }

    @Test
    fun `using orZero for positive infinity should return positive infinity`() {
        val value: Double? = Double.POSITIVE_INFINITY
        assertEquals(Double.POSITIVE_INFINITY, value.orZero())
    }

    @Test
    fun `using orZero for NaN should return NaN`() {
        val value: Double? = Double.NaN
        assertEquals(true, value.orZero().isNaN())
    }

}