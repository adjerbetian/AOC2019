package day5

import intCode.IntCodeComputer
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day5/input.txt")
    val computer = IntCodeComputer(program)

    computer.addInput(1)
    computer.run()
    println("outputs for part 1 is ${computer.outputs}")

    computer.reset()

    computer.addInput(5)
    computer.run()
    println("outputs for part 2 is ${computer.outputs}")
}
