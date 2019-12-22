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
        assertEquals(expected, map.asteroids)
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

    @Test
    fun vaporizeSimple() {
        val map = AsteroidMap(
            """
                .#....#####...#..
                ##...##.#####..##
                ##...#...#.#####.
                ..#.....X...###..
                ..#.#.....#....##
            """.trimIndent()
        )
        map.station = Point(8, 3)

        assertEquals(Point(8, 1), map.vaporize()) // 1
        assertEquals(Point(9, 0), map.vaporize()) // 2
        assertEquals(Point(9, 1), map.vaporize()) // 3
        assertEquals(Point(10, 0), map.vaporize()) // 4
        assertEquals(Point(9, 2), map.vaporize()) // 5
        assertEquals(Point(11, 1), map.vaporize()) // 6
        assertEquals(Point(12, 1), map.vaporize()) // 7
        assertEquals(Point(11, 2), map.vaporize()) // 8
        assertEquals(Point(15, 1), map.vaporize()) // 9

        assertEquals(Point(12, 2), map.vaporize()) // 1
        assertEquals(Point(13, 2), map.vaporize()) // 2
        assertEquals(Point(14, 2), map.vaporize()) // 3
        assertEquals(Point(15, 2), map.vaporize()) // 4
        assertEquals(Point(12, 3), map.vaporize()) // 5
        assertEquals(Point(16, 4), map.vaporize()) // 6
        assertEquals(Point(15, 4), map.vaporize()) // 7
        assertEquals(Point(10, 4), map.vaporize()) // 8
        assertEquals(Point(4, 4), map.vaporize()) // 9

        assertEquals(Point(2, 4), map.vaporize()) // 1
        assertEquals(Point(2, 3), map.vaporize()) // 2
        assertEquals(Point(0, 2), map.vaporize()) // 3
        assertEquals(Point(1, 2), map.vaporize()) // 4
        assertEquals(Point(0, 1), map.vaporize()) // 5
        assertEquals(Point(1, 1), map.vaporize()) // 6
        assertEquals(Point(5, 2), map.vaporize()) // 7
        assertEquals(Point(1, 0), map.vaporize()) // 8
        assertEquals(Point(5, 1), map.vaporize()) // 9
    }

    @Test
    fun vaporizeComplex() {
        val map = AsteroidMap(
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
        )

        assertEquals(Point(11, 12), map.vaporize())
        assertEquals(Point(12, 1), map.vaporize())
        assertEquals(Point(12, 2), map.vaporize())

        repeat(6) { map.vaporize() }
        assertEquals(Point(12, 8), map.vaporize()) // 10

        repeat(9) { map.vaporize() }
        assertEquals(Point(16, 0), map.vaporize()) // 20

        repeat(29) { map.vaporize() }
        assertEquals(Point(16, 9), map.vaporize()) // 50

        repeat(49) { map.vaporize() }
        assertEquals(Point(10, 16), map.vaporize()) // 100

        repeat(98) { map.vaporize() }
        assertEquals(Point(9, 6), map.vaporize()) // 199
        assertEquals(Point(8, 2), map.vaporize()) // 200
        assertEquals(Point(10, 9), map.vaporize()) // 201
    }
}