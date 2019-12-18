package IntCode

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IntCodeComputerTest {
    @Test
    fun simpleAddition() {
        val computer = IntCodeComputer(intArrayOf(1, 0, 0, 0, 99))

        computer.run()

        val result = computer.memory
        assertArrayEquals(intArrayOf(2, 0, 0, 0, 99), result)
    }

    @Test
    fun simpleMultiplication() {
        val computer = IntCodeComputer(intArrayOf(2, 3, 0, 3, 99))

        computer.run()

        val result = computer.memory
        assertArrayEquals(intArrayOf(2, 3, 0, 6, 99), result)
    }

    @Test
    fun simpleCombination() {
        val computer = IntCodeComputer(intArrayOf(2, 4, 4, 5, 99, 0))

        computer.run()

        val result = computer.memory
        assertArrayEquals(intArrayOf(2, 4, 4, 5, 99, 9801), result)
    }

    @Test
    fun complexCombination() {
        val computer = IntCodeComputer(intArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99))

        computer.run()

        val result = computer.memory
        assertArrayEquals(intArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99), result)
    }
}