package day14

import java.io.File

fun main() {
    val reactions = readInput("src/day14/input.txt")
    val system = SystemOfReaction(reactions)

    println("Min number of ORE: ${system.computeMinOREFor("FUEL")}")

    system.reset()

//    println("Min number of ORE: ${system.computeMinOREFor(Ingredient(1000000000000, "FUEL"))}")
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
