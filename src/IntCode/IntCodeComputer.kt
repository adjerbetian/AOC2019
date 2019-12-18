package IntCode

typealias IntCodeProgram = IntArray

const val ADD = 1
const val MULT = 2
const val END = 99


class IntCodeComputer(private val program: IntCodeProgram) {
    var memory = program.clone()
    var instructionPointer = 0

    fun reset() {
        instructionPointer = 0
        memory = program.clone()
    }

    fun run() {
        while (memory[instructionPointer] != END)
            treatInstruction()
    }

    private fun treatInstruction() {
        val instruction = memory[instructionPointer]

        if (instruction == ADD) {
            // *c = *a + *b
            val a = memory[instructionPointer + 1]
            val b = memory[instructionPointer + 2]
            val c = memory[instructionPointer + 3]
            memory[c] = memory[a] + memory[b]
            instructionPointer += 4
            return
        }
        if (instruction == MULT) {
            // *c = *a * *b
            val a = memory[instructionPointer + 1]
            val b = memory[instructionPointer + 2]
            val c = memory[instructionPointer + 3]
            memory[c] = memory[a] * memory[b]
            instructionPointer += 4
            return
        }
    }
}
