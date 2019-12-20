package day7

import intCode.IntCodeProgram

class AmplificationCircuit(program: IntCodeProgram, phases: IntArray) {
    private val amplifiers = phases.map { phase -> Amplifier(program, phase) }

    init {
        amplifiers.reduce { a, b -> a.branchTo(b); b }
    }

    fun run() {
        amplifiers.first().run(mutableListOf(0))
    }

    fun getOutput(): Int {
        return amplifiers.last().getOutput()
    }
}