package intCode

typealias IntCodeProgram = IntArray
typealias ParameterMode = Int

const val END = 99
const val POSITION = 0
const val IMMEDIATE = 1
const val RELATIVE = 2

class IntCodeComputer(private val program: IntCodeProgram) {
    var memory = Memory(program)
    var instructionPointer = 0
    private var inputWaiter = InputWaiter()
    var outputs = mutableListOf<Int>()
    var outputFunction: (output: Int) -> Unit = {}
    var relativeBase = 0

    fun reset() {
        instructionPointer = 0
        memory = Memory(program)
        inputWaiter = InputWaiter()
        outputs = mutableListOf()
    }

    fun run() {
        while (memory[instructionPointer] != END)
            treatInstruction()
    }

    private fun treatInstruction() {
        val opcode = memory[instructionPointer] % 100

        for (instruction in instructions) {
            if (instruction.matches(opcode)) {
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
        if (mode == RELATIVE)
            return memory[parameter + relativeBase]

        throw Error("unknown mode $mode")
    }

    private fun getParamMode(index: Int): ParameterMode {
        val instruction = memory[instructionPointer]
        var mode = instruction / 100
        for (i in 1 until index)
            mode /= 10
        return mode % 10
    }

    fun addInput(x: Int) {
        inputWaiter.addInput(x)
    }

    fun waitForNextInput(): Int {
        return inputWaiter.waitForNextInput()
    }

    fun getState(): IntArray {
        return memory.getState()
    }
}

class Memory(initialValue: IntCodeProgram) {
    private var state = initialValue.clone()

    operator fun get(i: Int): Int {
        stretchMemory(i)
        return state[i]
    }

    operator fun set(i: Int, value: Int) {
        stretchMemory(i)
        state[i] = value
    }

    private fun stretchMemory(i: Int) {
        if(state.size <= i) {
            state += IntArray(i - state.size + 1)
        }
    }

    fun getState(): IntArray {
        return state
    }

    override fun toString(): String {
        return state.toList().toString()
    }
}
