package day13

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import intCode.readIntCodeProgram

fun main() {
    val program = readIntCodeProgram("src/day13/input.txt")
    runPart1(program)
}

fun runPart1(program: IntCodeProgram) {
    val computer = IntCodeComputer(program)

    var nBlockTiles = 0

    var i = 0
    val values = longArrayOf(0, 0, 0)
    computer.outputFunction = { x ->
        values[i++] = x
        if (i == 3) {
            if (values[2] == 2L) nBlockTiles++
            i = 0
        }
    }

    computer.run()

    println("There are $nBlockTiles block tiles")
}
