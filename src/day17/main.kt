package day17

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day17/input.txt")
    runPart1(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)
    computer.run()

    val camera = Camera(computer.outputs)
    val intersections = camera.getIntersections()

    println(camera.toString())

    val result = intersections.sumBy { it.getAlignmentParameter() }
    println("The sum of the alignment parameters is $result")
}