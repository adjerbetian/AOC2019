package IntCode

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IntCodeComputerTest {
    @Test
    fun simpleAddition() {
        val computer = IntCodeComputer(intArrayOf(1, 0, 0, 0, 99))

        computer.run()

        assertArrayEquals(intArrayOf(2, 0, 0, 0, 99), computer.memory)
    }

    @Test
    fun simpleMultiplication() {
        val computer = IntCodeComputer(intArrayOf(2, 3, 0, 3, 99))

        computer.run()

        assertArrayEquals(intArrayOf(2, 3, 0, 6, 99), computer.memory)
    }

    @Test
    fun simpleCombination() {
        val computer = IntCodeComputer(intArrayOf(2, 4, 4, 5, 99, 0))

        computer.run()

        assertArrayEquals(intArrayOf(2, 4, 4, 5, 99, 9801), computer.memory)
    }

    @Test
    fun complexCombination() {
        val computer = IntCodeComputer(intArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99))

        computer.run()

        assertArrayEquals(intArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99), computer.memory)
    }

    @Test
    fun immediateParameterModeMultiplication() {
        val computer = IntCodeComputer(intArrayOf(1002, 4, 3, 4, 33))

        computer.run()

        assertArrayEquals(intArrayOf(1002, 4, 3, 4, 99), computer.memory)
    }

    @Test
    fun immediateParameterModeAddition() {
        val computer = IntCodeComputer(intArrayOf(1001, 4, 66, 4, 33))

        computer.run()

        assertArrayEquals(intArrayOf(1001, 4, 66, 4, 99), computer.memory)
    }

    @Test
    fun inputs() {
        val computer = IntCodeComputer(intArrayOf(3, 2, 0))
        computer.inputs = intArrayOf(99)

        computer.run()

        assertArrayEquals(intArrayOf(3, 2, 99), computer.memory)
    }

    @Test
    fun outputs() {
        val computer = IntCodeComputer(intArrayOf(4, 2, 104, 66, 99))

        computer.run()

        assertArrayEquals(intArrayOf(4, 2, 104, 66, 99), computer.memory)
        assertEquals(2, computer.outputs.size)
        assertEquals(104, computer.outputs[0])
        assertEquals(66, computer.outputs[1])
    }
}