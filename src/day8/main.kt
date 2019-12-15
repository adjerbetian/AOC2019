package day8

import java.io.File

fun main() {
    val input = readInput("src/day8/input.txt")
    val image = SpaceImage(25, 6, input)
    val layer = image.findMinLayerBy { it.getNumberOf(0) }
    println("Number of 1 * 2 of the 0 min layer: ${layer.getNumberOf(1) * layer.getNumberOf(2)}")
}

fun readInput(stringPath: String): String {
    return File(stringPath).readLines(Charsets.UTF_8)[0]
}
