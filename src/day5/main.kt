package day5

import intCode.IntCodeComputer
import java.io.File

fun main() {
    val program = readInput("src/day5/input.txt")
    val computer = IntCodeComputer(program)

    computer.addInput(1)
    computer.run()
    println("outputs for part 1 is ${computer.outputs}")

    computer.reset()

    computer.addInput(5)
    computer.run()
    println("outputs for part 2 is ${computer.outputs}")
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()