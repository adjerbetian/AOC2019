package day10

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AsteroidMapTest {
    @Test
    fun getAll() {
        val map = AsteroidMap(
            """
                .#.
                .##
            """.trimIndent()
        )

        val expected = listOf(
            Point(1, 0),
            Point(1, 1),
            Point(2, 1)
        )
        assertEquals(expected, map.getAll())
    }

    @Test
    fun get() {
        val map = AsteroidMap(
            """
                .#.
                .##
            """.trimIndent()
        )

        assertFalse(map[0, 0])
        assertTrue(map[1, 0])
        assertFalse(map[2, 0])
        assertFalse(map[0, 1])
        assertTrue(map[1, 1])
        assertTrue(map[2, 1])
    }

    @Test
    fun getNumberDetectableAsteroidsFrom() {
        val map = AsteroidMap(
            """
                .#..#
                .....
                #####
                ....#
                ...##
            """.trimIndent()
        )

        assertEquals(7, map.getNumberDetectableAsteroidsFrom(Point(1, 0)))
        assertEquals(7, map.getNumberDetectableAsteroidsFrom(Point(4, 0)))
        assertEquals(6, map.getNumberDetectableAsteroidsFrom(Point(0, 2)))
        assertEquals(7, map.getNumberDetectableAsteroidsFrom(Point(1, 2)))
        assertEquals(7, map.getNumberDetectableAsteroidsFrom(Point(2, 2)))
        assertEquals(7, map.getNumberDetectableAsteroidsFrom(Point(3, 2)))
        assertEquals(5, map.getNumberDetectableAsteroidsFrom(Point(4, 2)))
        assertEquals(7, map.getNumberDetectableAsteroidsFrom(Point(4, 3)))
        assertEquals(8, map.getNumberDetectableAsteroidsFrom(Point(3, 4)))
        assertEquals(7, map.getNumberDetectableAsteroidsFrom(Point(4, 4)))
    }

    @Test
    fun bestMonitoringStation() {
        assertEquals(
            Point(3, 4), AsteroidMap(
                """
                    .#..#
                    .....
                    #####
                    ....#
                    ...##
                """.trimIndent()
            ).bestMonitoringStation()
        )

        assertEquals(
            Point(5, 8), AsteroidMap(
                """
                    ......#.#.
                    #..#.#....
                    ..#######.
                    .#.#.###..
                    .#..#.....
                    ..#....#.#
                    #..#....#.
                    .##.#..###
                    ##...#..#.
                    .#....####
                """.trimIndent()
            ).bestMonitoringStation()
        )
        assertEquals(
            Point(1, 2), AsteroidMap(
                """
                    #.#...#.#.
                    .###....#.
                    .#....#...
                    ##.#.#.#.#
                    ....#.#.#.
                    .##..###.#
                    ..#...##..
                    ..##....##
                    ......#...
                    .####.###.
                """.trimIndent()
            ).bestMonitoringStation()
        )
        assertEquals(
            Point(6, 3), AsteroidMap(
                """
                    .#..#..###
                    ####.###.#
                    ....###.#.
                    ..###.##.#
                    ##.##.#.#.
                    ....###..#
                    ..#.#..#.#
                    #..#.#.###
                    .##...##.#
                    .....#.#..
                """.trimIndent()
            ).bestMonitoringStation()
        )
        assertEquals(
            Point(11, 13), AsteroidMap(
                """
                    .#..##.###...#######
                    ##.############..##.
                    .#.######.########.#
                    .###.#######.####.#.
                    #####.##.#.##.###.##
                    ..#####..#.#########
                    ####################
                    #.####....###.#.#.##
                    ##.#################
                    #####.##.###..####..
                    ..######..##.#######
                    ####.##.####...##..#
                    .#####..#.######.###
                    ##...#.##########...
                    #.##########.#######
                    .####.#.###.###.#.##
                    ....##.##.###..#####
                    .#.#.###########.###
                    #.#.#.#####.####.###
                    ###.##.####.##.#..##
                """.trimIndent()
            ).bestMonitoringStation()
        )
    }
}