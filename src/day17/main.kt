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
    val image = ImageConverter.parse(computer.outputs)
    ImageConverter.print(image)

    val result = image.getIntersections().sumBy { it.getAlignmentParameter() }
    println("The sum of the alignment parameters is $result")
}