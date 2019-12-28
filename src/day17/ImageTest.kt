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
}