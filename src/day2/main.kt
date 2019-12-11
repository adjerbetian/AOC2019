package day2

import java.io.File

fun main() {
    val program = readInput("src/day2/input.txt")

    program[1] = 12
    program[2] = 2
    treatOpcode(program, 0)
    println(program[0])
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()