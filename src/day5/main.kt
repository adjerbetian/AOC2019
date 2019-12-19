package day5

import IntCode.IntCodeComputer
import java.io.File

fun main() {
    val program = readInput("src/day5/input.txt")
    val computer = IntCodeComputer(program)
    computer.run(intArrayOf(1))
    println("outputs for part 1 is ${computer.outputs}")

    computer.reset()

    computer.run(intArrayOf(5))
    println("outputs for part 2 is ${computer.outputs}")
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()