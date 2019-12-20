package day7

import intCode.IntCodeComputer
import intCode.IntCodeProgram
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AmplificationCircuit(program: IntCodeProgram, phases: List<Int>) {
    private val amplifiers = phases.map { phase ->
        val amplifier = IntCodeComputer(program)
        amplifier.addInput(phase)
        amplifier
    }

    init {
        amplifiers.reduce { a, b ->
            a.outputFunction = { b.addInput(it) }
            b
        }
        amplifiers.last().outputFunction = { amplifiers.first().addInput(it) }
    }

    fun run() {
        amplifiers.first().addInput(0)
        runBlocking {
            val promises = amplifiers.map {
                GlobalScope.async {
                    it.run()
                }
            }
            promises.forEach { it.await() }
        }
    }

    fun getOutput(): Int {
        return amplifiers.last().outputs.last()
    }
}