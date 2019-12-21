package intCode

import kotlinx.coroutines.*

val context = newSingleThreadContext("InputWaiterContext")

class InputWaiter() {
    private val inputs = mutableListOf<IntCode>()

    fun addInput(x: IntCode) {
        inputs.add(x)
    }

    fun waitForNextInput(): IntCode {
        return runBlocking {
            withContext(context) {
                while (inputs.isEmpty())
                    yield()

                popInput()
            }
        }
    }

    private fun popInput(): IntCode {
        val input = inputs.first()
        inputs.removeAt(0)
        return input
    }
}