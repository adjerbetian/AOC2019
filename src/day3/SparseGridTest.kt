package day3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SparseGridTest {
    @Test
    fun newGridShouldBeEmpty() {
        val g = SparseGrid()

        assertFalse(g.has(Position(0, 0)))
    }

    @Test
    fun insertedPositionsShouldAppear() {
        val g = SparseGrid()

        g.insert(Position(0, 0))
        g.insert(Position(1, 0))

        assertTrue(g.has(Position(0, 0)))
        assertTrue(g.has(Position(1, 0)))
    }

    @Test
    fun insertPath() {
        val g = SparseGrid()
        val path = listOf(Up(3), Left(2))

        g.insert(path)

        assertTrue(g.has(Position(0, 0)))
        assertTrue(g.has(Position(0, 1)))
        assertTrue(g.has(Position(0, 2)))
        assertTrue(g.has(Position(0, 3)))
        assertTrue(g.has(Position(-1, 3)))
        assertTrue(g.has(Position(-2, 3)))
    }

    @Test
    fun getSingleIntersectionWithPath() {
        val g = SparseGrid()
        g.insert(listOf(Right(2), Up(2)))

        val result = g.getIntersectionWith(listOf(Up(1), Right(3)))

        assertEquals(Position(2, 1), result[0])
    }

    @Test
    fun getMultipleIntersectionsWithPath() {
        val g = SparseGrid()
        g.insert(listOf(Up(10)))

        val result = g.getIntersectionWith(
            listOf(
                Right(1),
                Up(1),

                Left(2),
                Up(1),

                Right(2),
                Up(1),

                Left(2),
                Up(1)
            )
        )

        assertEquals(3, result.size)
        assertTrue(result.containsAll(listOf(Position(0, 1), Position(0, 2), Position(0, 3))))
    }
}
