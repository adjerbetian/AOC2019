package day15

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day15/input.txt")
    runPart1(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val game = Game(computer)

    game.explorePathToOxygen()
    game.printMap()
    val path = game.getPathToOxygen()
    println("Path length: ${path.size}")
}
