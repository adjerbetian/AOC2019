package day11

import intCode.IntCodeComputer
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day11/input.txt")
    val computer = IntCodeComputer(program)
    val robot = PaintingRobot()
    robot.linkToComputer(computer)

    computer.run()

    println("Number of painted panels: ${robot.paintedPanels.size}")
}
