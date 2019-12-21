package intCode

import java.util.concurrent.LinkedBlockingDeque

class InputWaiter {
    private val inputs = LinkedBlockingDeque<IntCode>()

    fun addInput(x: IntCode) {
        inputs.add(x)
    }

    fun takeInput(): IntCode {
        return inputs.take()
    }
}