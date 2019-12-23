package day13

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day13/input.txt")
    runPart1(program)
    runPart2(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val game = Game()
    game.linkToComputer(computer)

    computer.run()

    println("Number of Block tiles: ${game.tiles.count { it is BlockTile }}")
}

fun runPart2(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val game = Game()
    game.linkToComputer(computer, verbose = false)

    computer.memory[0] = 2
    computer.run()

    println("The score is ${game.score}")
}
