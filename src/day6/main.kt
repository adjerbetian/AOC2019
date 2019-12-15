package day6

import java.io.File

fun main() {
    val orbitMap = readInput("src/day6/input.txt")
    val system = OrbitSystem(orbitMap)
    println("System orbits sum: ${system.getOrbitsSum()}")
}

fun readInput(stringPath: String): OrbitMap {
    return File(stringPath).readLines(Charsets.UTF_8)
}
