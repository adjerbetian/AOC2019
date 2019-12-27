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
    val explorer = OxygenShipExplorer(computer)

    explorer.findOxygen()

    println(explorer)
    println("Path length: ${explorer.getPathToOxygen().size}")
}

fun runPart2(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val explorer = FullShipExplorer(computer)

    val shipMap = explorer.exploreMap()
    val steps = OxygenSpreader(shipMap).spreadOxygen()

    println("Steps to spread oxygen length: $steps")
}
