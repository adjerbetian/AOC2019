package day3

import java.io.File

fun main() {
    val (firstPath, secondPath) = readInput("src/day3/input.txt")
    val result = findIntersection(firstPath, secondPath)
    println("First intersection is at distance: $result")
}

fun readInput(stringPath: String): Pair<Path, Path> {
    val (firstStringPath, secondStringPath) = File(stringPath).readLines(Charsets.UTF_8)
    return Pair(parseStringPath(firstStringPath), parseStringPath(secondStringPath))
}
fun parseStringPath(stringPath: String): Path {
    return stringPath.split(",").map { buildDirection(it) }
}
