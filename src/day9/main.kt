package day9

import intCode.IntCodeComputer
import java.io.File

fun main() {
    val program = readInput("src/day9/input.txt")
    val computer = IntCodeComputer(program)

    computer.addInput(1)
    computer.run()
    println("Result for part 1: ${computer.outputs}")
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()
