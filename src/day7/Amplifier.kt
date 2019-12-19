package day7

import intCode.IntCodeComputer
import intCode.IntCodeProgram

class Amplifier(program: IntCodeProgram, val phase: Int) {
    val computer = IntCodeComputer(program)
    var outputAmplifier: Amplifier? = null

    fun run(inputs: IntArray = intArrayOf()) {
        computer.run(intArrayOf(phase) + inputs)
        outputAmplifier?.run(computer.outputs.toIntArray())
    }

    fun branchTo(amp: Amplifier) {
        outputAmplifier = amp
    }

    fun getOutput(): Int {
        return computer.outputs[0]
    }
}

class AmplificationCircuit(program: IntCodeProgram, phases: IntArray) {
    private val amplifiers = phases.map { phase -> Amplifier(program, phase) }

    init {
        amplifiers.reduce { a, b -> a.branchTo(b); b }
    }

    fun run() {
        amplifiers.first().run(intArrayOf(0))
    }

    fun getOutput(): Int {
        return amplifiers.last().getOutput()
    }
}
