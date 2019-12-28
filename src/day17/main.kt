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
    val camera = Camera(computer)

    val image = camera.getImage()

    ImagePrinter(image).print()

    val result = image.getIntersections().sumBy { it.getAlignmentParameter() }
    println("The sum of the alignment parameters is $result")
}