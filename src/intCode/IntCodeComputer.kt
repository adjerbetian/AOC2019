package intCode

typealias IntCodeProgram = LongArray
typealias IntCode = Long

const val END = 99L
const val POSITION = 0
const val IMMEDIATE = 1
const val RELATIVE = 2

class IntCodeComputer(private val program: IntCodeProgram) {
    var memory = Memory(program)
    private var inputWaiter = InputWaiter()

    var instructionPointer = 0L
    var outputs = mutableListOf<Long>()
    var outputFunction: (output: Long) -> Unit = {}
    var relativeBase = 0L

    fun reset() {
        instructionPointer = 0
        memory = Memory(program)
        inputWaiter = InputWaiter()
        outputs = mutableListOf()
        relativeBase = 0L
    }

    fun run() {
        while (memory[instructionPointer] != END)
            treatInstruction()
    }

    private fun treatInstruction() {
        val opcode = (memory[instructionPointer] % 100).toInt()

        for (instruction in instructions) {
            if (instruction.matches(opcode)) {
                instruction.run(this)
                return
            }
        }

        throw Error("unknown opcode $opcode")
    }

    fun getParamValue(index: Int): IntCode {
        val mode = getParamMode(index)
        val parameter = getParam(index)

        if (mode == POSITION)
            return memory[parameter]
        if (mode == IMMEDIATE)
            return parameter
        if (mode == RELATIVE)
            return memory[parameter + relativeBase]

        throw Error("unknown mode $mode")
    }

    fun getParamDest(index: Int): IntCode {
        val mode = getParamMode(index)
        val parameter = getParam(index)

        if (mode == POSITION)
            return parameter
        if (mode == IMMEDIATE)
            return parameter
        if (mode == RELATIVE)
            return parameter + relativeBase

        throw Error("unknown mode $mode")
    }

    private fun getParam(index: Int): IntCode {
        return memory[instructionPointer + index]
    }

    private fun getParamMode(index: Int): Int {
        val instruction = memory[instructionPointer].toInt()
        var mode = instruction / 100
        for (i in 1 until index)
            mode /= 10
        return mode % 10
    }

    fun addInput(x: IntCode) {
        inputWaiter.addInput(x)
    }

    fun waitForNextInput(): IntCode {
        return inputWaiter.waitForNextInput()
    }

    fun getState(): IntCodeProgram {
        return memory.getState()
    }
}

