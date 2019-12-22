package day11

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day11/input.txt")
    runPart1(program)
    runPart2(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val robot = PaintingRobot()
    robot.linkToComputer(computer)

    computer.run()

    println("Number of painted panels: ${robot.paintedPanels.size}")
}

fun runPart2(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    val robot = PaintingRobot(1)
    robot.linkToComputer(computer)

    computer.run()

    println(robot)
}
