package intCode

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.random.Random

class Counter {
    private var i = 0

    fun get() = i++

    fun getAsText() = (i++).toString()
}

val counter = Counter()

class InputWaiter(val name: String = counter.getAsText()) {
    private val inputs = mutableListOf<IntCode>()
    private var isWaiting = false

    fun addInput(x: IntCode) {
        inputs.add(x)
    }

    fun waitForNextInput(): IntCode {
        setWaiting(true)
        return runBlocking {
            while (inputs.isEmpty()) {
//                yield()
                delay(Random.nextLong(0, 10))
            }
            val input = popInput()
            setWaiting(false)
            input
        }
    }

    private fun popInput(): IntCode {
        val input = inputs.first()
        inputs.removeAt(0)
        return input
    }

    private fun setWaiting(value: Boolean) {
        if (isWaiting == value)
            throw Error("already is state $isWaiting")
        isWaiting = value
    }

    override fun toString(): String {
        return "$name: $inputs"
    }
}