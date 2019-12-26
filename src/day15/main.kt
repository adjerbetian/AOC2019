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

    computer.run()

    println("Smallest number of steps: ${game.distances.size}")
}
