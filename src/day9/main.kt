package day9

import intCode.IntCodeComputer
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day9/input.txt")
    val computer = IntCodeComputer(program)

    computer.addInput(1)
    computer.run()
    println("Result for part 1: ${computer.outputs}")

    computer.reset()

    computer.addInput(2)
    computer.run()
    println("Result for part 2: ${computer.outputs}")
}
