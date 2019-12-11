package day2

import java.io.File

fun main() {
    val program = readInput("src/day2/input.txt")

    val memory = program.clone()
    memory[1] = 12
    memory[2] = 2
    treatOpcode(memory)
    println("First result: ${memory[0]}")


    val (noun, verb) = findNounAndVerbFor(program, 19690720)
    println("Second result: ${100 * noun + verb}")
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()