package intCode

typealias IntCodeProgram = IntArray
typealias ParameterMode = Int

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
        inputPointer = 0
        outputs = mutableListOf()
    }

    fun run(newInputs: IntArray = intArrayOf()) {
        inputs = newInputs

        while (memory[instructionPointer] != END)
            treatInstruction()
    }

    private fun treatInstruction() {
        val opcode = memory[instructionPointer] % 100

        for(instruction in instructions) {
            if(instruction.matches(opcode)) {
                instruction.run(this)
                return
            }
        }

        throw Error("unknown opcode $opcode")
    }

    fun getParam(index: Int): Int {
        return memory[instructionPointer + index]
    }

    fun getParamValue(index: Int): Int {
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
