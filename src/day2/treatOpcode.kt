package day2

const val ADD = 1
const val MULT = 2
const val END = 99

data class NounVerb(val noun: Int, val verb: Int)

fun findNounAndVerbFor(program: IntArray, expectedResult: Int): NounVerb {
    for (noun in 0..99) {
        for (verb in 0..99) {
            val memory = program.clone()
            memory[1] = noun
            memory[2] = verb
            treatOpcode(memory)
            if (memory[0] == expectedResult) {
                return NounVerb(noun, verb)
            }
        }
    }
    throw Error("Not found")
}

fun treatOpcode(program: IntArray, position: Int = 0) {
    if (program[position] == END) return

    if (program[position] == ADD) {
        program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]]
    } else if (program[position] == MULT) {
        program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]]
    }

    return treatOpcode(program, position + 4)
}