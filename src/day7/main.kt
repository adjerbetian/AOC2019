package day7

import intCode.IntCodeProgram
import intCode.readIntCodeProgram
import java.lang.Integer.max

fun main() {
    val program = readIntCodeProgram("src/day7/input.txt")

    val part1 = findMaxThrustForPhases(program, listOf(0, 1, 2, 3, 4))
    println("Part 1: Max thruster signal is $part1")

    val part2 = findMaxThrustForPhases(program, listOf(5, 6, 7, 8, 9))
    println("Part 2: Max thruster signal is $part2")
}

fun findMaxThrustForPhases(program: IntCodeProgram, phases: List<Int>): Int {
    var maxOutput: Int = Int.MIN_VALUE
    val permutations = permutations(phases)

    for (phase in permutations) {
        val circuit = AmplificationCircuit(program, phase)
        circuit.run()
        maxOutput = max(maxOutput, circuit.getOutput())
        println("\t$phase --> thrust = ${circuit.getOutput()} --> max = $maxOutput")
    }

    return maxOutput
}