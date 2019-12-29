package day17

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day17/input.txt")
    runPart1(program)
    runPart2(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)

    computer.run()
    val image = ImageConverter.parse(computer.outputs)
    ImageConverter.print(image)

    val result = image.getIntersections().sumBy { it.getAlignmentParameter() }
    println("The sum of the alignment parameters is $result")
}

fun runPart2(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)

    computer.run()
    val image = ImageConverter.parse(computer.outputs)

    val result = image.getScaffoldPath()
    println("The path is $result")

    val routine = listOf('A', 'B', 'B', 'A', 'C', 'A', 'C', 'A', 'C', 'B')
    val A = listOf("L", "6", "R", "12", "R", "8")
    val B = listOf("R", "8", "R", "12", "L", "12")
    val C = listOf("R", "12", "L", "12", "L", "4", "L", "4")
    val videoFeed = listOf("n")

    val instructions = listOf(
        routine.joinToString(","),
        A.joinToString(","),
        B.joinToString(","),
        C.joinToString(","),
        videoFeed.joinToString(",")
    ).joinToString("\n") + "\n"

    computer.reset()
    instructions.forEach { computer.addInput(it.toLong()) }
    computer.memory[0] = 2
    computer.run()
    println("Number of collected dust: ${computer.outputs.last()}")
}