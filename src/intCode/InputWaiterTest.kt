package intCode

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

internal class InputWaiterTest {
    @Test
    fun simple1() = runBlocking {
        val waiter = InputWaiter("waiter")

        waiter.addInput(2)

        assertEquals(2, waiter.waitForNextInput())
    }

    @Test
    fun simple2() = runBlocking {
        val waiter = InputWaiter("waiter")

        waiter.addInput(1)
        waiter.addInput(2)
        waiter.addInput(3)

        assertEquals(1, waiter.waitForNextInput())
        assertEquals(2, waiter.waitForNextInput())
        assertEquals(3, waiter.waitForNextInput())
    }

    @Test
    fun complex() = runBlocking {
        val waiter = InputWaiter("waiter")

        GlobalScope.launch {
            delay(10)
            waiter.addInput(1)
            waiter.addInput(2)
            delay(10)
            waiter.addInput(3)
        }

        assertEquals(1, waiter.waitForNextInput())
        assertEquals(2, waiter.waitForNextInput())
        assertEquals(3, waiter.waitForNextInput())
    }

    @Disabled
    @Test
    fun complexMultiple() = runBlocking {
        val waiters = List(20) { InputWaiter() }

        val promises = waiters.map {
            listOf(
                GlobalScope.launch {
                    it.waitForNextInput()
                },
                GlobalScope.launch {
                    delay(2)
                    it.addInput(2)
                }
            )
        }

        promises.forEach { list -> list.forEach { it.join() } }
    }
}