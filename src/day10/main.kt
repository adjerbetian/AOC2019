package day10

import java.io.File

fun main() {
    val input = readInput("src/day10/input.txt")
    val map = AsteroidMap(input)
    val bestLocation = map.bestMonitoringStation()
    val result = map.getNumberDetectableAsteroidsFrom(bestLocation)
    println("Best location is $result")
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
