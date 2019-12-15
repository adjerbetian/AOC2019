package day3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindClosestIntersectionTest {
    @Test
    fun simpleExample() {
        val firstPath = parseStringPath("R8,U5,L5,D3")
        val secondPath = parseStringPath("U7,R6,D4,L4")

        val result = findClosestIntersection(firstPath, secondPath)

        assertEquals(6, result)
    }

    @Test
    fun secondExample() {
        val firstPath = parseStringPath("R75,D30,R83,U83,L12,D49,R71,U7,L72")
        val secondPath = parseStringPath("U62,R66,U55,R34,D71,R55,D58,R83")

        val result = findClosestIntersection(firstPath, secondPath)

        assertEquals(159, result)
    }

    @Test
    fun thirdExample() {
        val firstPath = parseStringPath("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51")
        val secondPath = parseStringPath("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")

        val result = findClosestIntersection(firstPath, secondPath)

        assertEquals(135, result)
    }
}
