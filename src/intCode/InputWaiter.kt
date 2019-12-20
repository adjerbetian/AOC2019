package intCode

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class InputWaiter {
    private val inputs = mutableListOf<Int>()

    fun addInput(x: Int) {
        inputs.add(x)
    }

    fun waitForNextInput(): Int {
        return runBlocking {
            while (inputs.isEmpty())
                delay(Random.nextLong(0, 5))
            popInput()
        }
    }

    private fun popInput(): Int {
        val input = inputs.first()
        inputs.removeAt(0)
        return input
    }
}