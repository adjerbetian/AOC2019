package day7

import intCode.IntCodeComputer
import intCode.IntCodeProgram

class Amplifier(program: IntCodeProgram, val phase: Int) {
    val computer = IntCodeComputer(program)
    var outputAmplifier: Amplifier? = null

    fun run(inputs: MutableList<Int> = mutableListOf()) {
        computer.addInput(phase)
        computer.addInputs(inputs)
        computer.run()
        outputAmplifier?.run(computer.outputs)
    }

    fun branchTo(amp: Amplifier) {
        outputAmplifier = amp
    }

    fun getOutput(): Int {
        return computer.outputs[0]
    }
}

