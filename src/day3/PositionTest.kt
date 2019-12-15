package day3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PositionTest {
    @Test
    fun moveShouldMoveUpWhenGivenUpDirection() {
        val result = Position(0, 0).move(Up(3))

        assertEquals(Position(0, 3), result)
    }

    @Test
    fun moveDown() {
        val result = Position(0, 0).move(Down(3))

        assertEquals(Position(0, -3), result)
    }

    @Test
    fun moveRight() {
        val result = Position(0, 0).move(Right(3))

        assertEquals(Position(3, 0), result)
    }

    @Test
    fun moveLeft() {
        val result = Position(0, 0).move(Left(3))

        assertEquals(Position(-3, 0), result)
    }


    @Test
    fun stepLeft() {
        val result = Position(0, 0).step(Left(3))

        assertEquals(Position(-1, 0), result)
    }

    @Test
    fun stepUp() {
        val result = Position(0, 0).step(Left(3))

        assertEquals(Position(-1, 0), result)
    }

    @Test
    fun getManhattanDistance() {
        val result = Position(-3, 5).getManhattanDistance()

        assertEquals(8, result)
    }
}
