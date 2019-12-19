package day7

import java.io.File
import java.lang.Integer.max

fun main() {
    val program = readInput("src/day7/input.txt")
    var maxOutput: Int = Int.MIN_VALUE
    val permutations = permutations(listOf(0, 1, 2, 3, 4))

    for(p in permutations) {
        val circuit = AmplificationCircuit(program, p.toIntArray())
        circuit.run()
        maxOutput = max(maxOutput, circuit.getOutput())
    }

    println("Part 1: Max thruster signal is $maxOutput")
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()