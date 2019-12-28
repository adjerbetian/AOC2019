package day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ImageConverterTest {
    @Test
    fun convertAndParse() {
        val input = """
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

        val image = ImageConverter.parse(input)
        val output = ImageConverter.stringify(image)

        assertEquals(input, output)
    }
}