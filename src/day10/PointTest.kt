package day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PointTest {

    @Test
    fun isBlockingSightBetween() {
        val from = Point(0, 0)
        val to = Point(2, 0)

        assertFalse(from.isBlockingSightBetween(from, to))
        assertFalse(to.isBlockingSightBetween(from, to))

        assertTrue(Point(1, 0).isBlockingSightBetween(from, to))
        assertFalse(Point(1, 2).isBlockingSightBetween(from, to))
        assertFalse(Point(-1, 0).isBlockingSightBetween(from, to))
    }
}