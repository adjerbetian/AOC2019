package day15

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day15/input.txt")
    runPart1(program)
    runPart2(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val finder = OxygenFinder(computer)

    finder.findOxygen()

    println(finder)
    println("Path length: ${finder.getPathToOxygen().size}")
}

fun runPart2(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val spreader = OxygenSpreader(computer)

    spreader.exploreMap()
    val steps = spreader.spreadOxygen()
    println("Steps to spread oxygen length: $steps")
}
