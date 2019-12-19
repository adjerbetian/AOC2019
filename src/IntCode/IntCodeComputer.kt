package IntCode

typealias IntCodeProgram = IntArray
typealias ParameterMode = Int

const val ADD = 1
const val MULT = 2
const val INPUT = 3
const val OUTPUT = 4
const val END = 99

const val POSITION = 0
const val IMMEDIATE = 1


class IntCodeComputer(private val program: IntCodeProgram) {
    var memory = program.clone()
    var instructionPointer = 0
    var inputs = intArrayOf()
    var inputPointer = 0
    var outputs = mutableListOf<Int>()

    fun reset() {
        instructionPointer = 0
        memory = program.clone()
        inputs = intArrayOf()
        outputs = mutableListOf()
    }

    fun run() {
        while (memory[instructionPointer] != END)
            treatInstruction()
    }

    private fun treatInstruction() {
        val instruction = memory[instructionPointer]
        val opcode = instruction % 100

        if (opcode == ADD) {
            memory[getParam(3)] = getParamValue(1) + getParamValue(2)
            instructionPointer += 4
            return
        }
        if (opcode == MULT) {
            memory[getParam(3)] = getParamValue(1) * getParamValue(2)
            instructionPointer += 4
            return
        }
        if (opcode == INPUT) {
            memory[getParam(1)] = inputs[inputPointer]
            inputPointer++
            instructionPointer += 2
            return
        }
        if (opcode == OUTPUT) {
            outputs.add(getParamValue(1))
            inputPointer++
            instructionPointer += 2
            return
        }
        throw Error("unknown opcode $opcode")
    }

    private fun getParam(index: Int): Int {
        return memory[instructionPointer + index]
    }

    private fun getParamValue(index: Int): Int {
        val mode = getParamMode(index)
        val parameter = getParam(index)

        if (mode == POSITION)
            return memory[parameter]
        if (mode == IMMEDIATE)
            return parameter

        throw Error("unknown mode $mode")
    }

    private fun getParamMode(index: Int): ParameterMode {
        val instruction = memory[instructionPointer]
        var mode = instruction / 100
        for (i in 1 until index)
            mode /= 10
        return mode % 10
    }
}
