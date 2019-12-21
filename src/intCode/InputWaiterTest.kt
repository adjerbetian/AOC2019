package intCode

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import kotlin.test.assertTrue

internal class InputWaiterTest {
    @Test
    fun simple1() = runBlocking {
        val waiter = InputWaiter()

        waiter.addInput(2)

        assertEquals(2, waiter.takeInput())
    }

    @Test
    fun simple2() = runBlocking {
        val waiter = InputWaiter()

        waiter.addInput(1)
        waiter.addInput(2)
        waiter.addInput(3)

        assertEquals(1, waiter.takeInput())
        assertEquals(2, waiter.takeInput())
        assertEquals(3, waiter.takeInput())
    }

    @Test
    fun complex() = runBlocking {
        val waiter = InputWaiter()

        GlobalScope.launch {
            delay(10)
            waiter.addInput(1)
            waiter.addInput(2)
            delay(10)
            waiter.addInput(3)
        }

        assertEquals(1, waiter.takeInput())
        assertEquals(2, waiter.takeInput())
        assertEquals(3, waiter.takeInput())
    }

    @Test
    fun complexMultiple() {
        val waiters = List(20) { InputWaiter() }
        val promises = mutableListOf<Deferred<IntCode>>()

        waiters.forEach { waiter ->
            promises.add(
                GlobalScope.async {
                    waiter.takeInput()
                }
            )
            promises.add(
                GlobalScope.async {
                    waiter.addInput(2)
                    2L
                }
            )
        }

        val results = runBlocking {
            promises.awaitAll()
        }
        assertEquals(waiters.size * 2, results.size)
        assertTrue(results.all { it == 2L })
    }

    @Disabled
    @Test
    fun complexMultipleWithDelay() {
        val waiters = List(20) { InputWaiter() }
        val promises = mutableListOf<Deferred<IntCode>>()

        waiters.forEach { waiter ->
            promises.add(
                GlobalScope.async {
                    waiter.takeInput()
                }
            )
            promises.add(
                GlobalScope.async {
                    delay(10L)
                    waiter.addInput(2)
                    2L
                }
            )
        }

        val results = runBlocking {
            promises.awaitAll()
        }
        assertEquals(waiters.size * 2, results.size)
        assertTrue(results.all { it == 2L })
    }
}