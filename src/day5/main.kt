package day5

import IntCode.IntCodeComputer
import java.io.File

fun main() {
    val program = readInput("src/day5/input.txt")
    val computer = IntCodeComputer(program)
    computer.inputs = intArrayOf(1)
    computer.run()
    println(computer.outputs)
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()