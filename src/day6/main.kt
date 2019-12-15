package day6

import java.io.File

fun main() {
    val orbitMap = readInput("src/day6/input.txt")
    val system = OrbitSystem(orbitMap)
    println("System orbits sum: ${system.getOrbitsSum()}")
    println("Orbital transfers from You to Santa: ${system.getNOrbitalTransfers(from = "YOU", to = "SAN") - 2}")
}

fun readInput(stringPath: String): OrbitMap {
    return File(stringPath).readLines(Charsets.UTF_8)
}
