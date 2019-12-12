package day3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PathIteratorTest {
    @Test
    fun firstPositionShouldBeTheOrigin() {
        val iterator = PathIterator(listOf())

        assertEquals(Position(0,0), iterator.getPosition())
    }

    @Test
    fun itShouldMoveInTheFirstDirection() {
        val iterator = PathIterator(listOf(Up(1)))

        val result = iterator.getNextPosition()

        assertEquals(Position(0, 1), result)
    }

    @Test
    fun itShouldMoveSeveralTimesInTheFirstDirection() {
        val iterator = PathIterator(listOf(Up(2)))

        iterator.getNextPosition()
        val result = iterator.getNextPosition()

        assertEquals(Position(0, 2), result)
    }

    @Test
    fun itShouldChangeDirection() {
        val iterator = PathIterator(listOf(Up(2), Right(2)))

        iterator.getNextPosition()
        iterator.getNextPosition()
        val result = iterator.getNextPosition()

        assertEquals(Position(1, 2), result)
    }

    @Test
    fun itShouldSayWhenItsFinished() {
        val iterator = PathIterator(listOf(Up(2), Right(2)))

        iterator.getNextPosition()
        assertFalse(iterator.isFinished())

        iterator.getNextPosition()
        assertFalse(iterator.isFinished())

        iterator.getNextPosition()
        assertFalse(iterator.isFinished())

        iterator.getNextPosition()
        assertTrue(iterator.isFinished())
    }
}
