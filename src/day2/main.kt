package day2

import intCode.IntCodeComputer
import java.io.File

fun main() {
    val program = readInput("src/day2/input.txt")

    val computer = IntCodeComputer(program)
    computer.memory[1] = 12
    computer.memory[2] = 2
    computer.run()
    println("First result: ${computer.memory[0]}")

    val (noun, verb) = findNounAndVerbFor(computer, 19690720)
    println("Second result: ${100 * noun + verb}")
}

fun readInput(path: String) = File(path).readText(Charsets.UTF_8).split(",").map { it.toInt() }.toIntArray()

fun findNounAndVerbFor(computer: IntCodeComputer, expectedResult: Int): NounVerb {
    for (noun in 0..99) {
        for (verb in 0..99) {
            computer.reset()
            computer.memory[1] = noun
            computer.memory[2] = verb
            computer.run()
            if (computer.memory[0] == expectedResult)
                return NounVerb(noun, verb)
        }
    }
    throw Error("Not found")
}

data class NounVerb(val noun: Int, val verb: Int)

