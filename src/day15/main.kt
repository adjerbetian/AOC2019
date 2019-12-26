package day15

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day15/input.txt")
    runPart1(program)
//    runPart2(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val game = PathGame(computer)

    game.explorePathToOxygen()
    game.printMap()
    val path = game.getPathToOxygen()
    println("Path length: ${path.size}")
}

//fun runPart2(program: IntCodeProgram) {
//    val computer = IntCodeComputer(program)
//    val game = Game(computer)
//
//    game.exploreMap()
//    game.printMap()
//    val path = game.getPathToOxygen()
//    println("Path length: ${path.size}")
//}
