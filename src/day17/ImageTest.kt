package day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ImageTest {
    @Test
    fun isIntersection() {
        val image = ImageConverter.parse(
            """
                ..#..........
                ..#..........
                #######...###
                #.#...#...#.#
                #############
                ..#...#...#..
                ..#####...^..
            """.trimIndent()
        )

        val output = ImageConverter.stringifyWith(image) { p ->
            if (image.isIntersection(p)) "O"
            else null
        }

        assertEquals(
            """
                ..#..........
                ..#..........
                ##O####...###
                #.#...#...#.#
                ##O###O###O##
                ..#...#...#..
                ..#####...^..
            """.trimIndent(), output
        )
    }

    @Test
    fun getPath() {
        val image = ImageConverter.parse(
            """
                #######...#####
                #.....#...#...#
                #.....#...#...#
                ......#...#...#
                ......#...###.#
                ......#.....#.#
                ^########...#.#
                ......#.#...#.#
                ......#########
                ........#...#..
                ....#########..
                ....#...#......
                ....#...#......
                ....#...#......
                ....#####......
            """.trimIndent()
        )

        val path = image.getScaffoldPath()

        assertEquals(
            listOf(
                "R",
                "8",
                "R",
                "8",
                "R",
                "4",
                "R",
                "4",
                "R",
                "8",
                "L",
                "6",
                "L",
                "2",
                "R",
                "4",
                "R",
                "4",
                "R",
                "8",
                "R",
                "8",
                "R",
                "8",
                "L",
                "6",
                "L",
                "2"
            ), path
        )
    }
}