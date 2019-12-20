package intCode

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

        computer.addInput(99)
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

    @Test
    fun outputFunction() {
        val outputs = mutableListOf<Int>()
        val computer = IntCodeComputer(intArrayOf(4, 2, 104, 66, 99))
        computer.outputFunction = { x: Int -> outputs.add(x) }

        computer.run()

        assertEquals(2, outputs.size)
        assertEquals(104, outputs[0])
        assertEquals(66, outputs[1])
    }

    @Test
    fun day5Complex1() {
        val computer = IntCodeComputer(intArrayOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8))

        computer.addInput(3)
        computer.run()
        assertEquals(0, computer.outputs[0])

        computer.reset()

        computer.addInput(8)
        computer.run()
        assertEquals(1, computer.outputs[0])
    }

    @Test
    fun day5Complex2() {
        val computer = IntCodeComputer(intArrayOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8))

        computer.addInput(10)
        computer.run()
        assertEquals(0, computer.outputs[0])

        computer.reset()

        computer.addInput(3)
        computer.run()
        assertEquals(1, computer.outputs[0])
    }

    @Test
    fun day5Complex3() {
        val computer = IntCodeComputer(intArrayOf(3, 3, 1108, -1, 8, 3, 4, 3, 99))

        computer.addInput(8)
        computer.run()
        assertEquals(1, computer.outputs[0])

        computer.reset()

        computer.addInput(4)
        computer.run()
        assertEquals(0, computer.outputs[0])
    }

    @Test
    fun day5Complex4() {
        val computer = IntCodeComputer(intArrayOf(3, 3, 1107, -1, 8, 3, 4, 3, 99))

        computer.addInput(10)
        computer.run()
        assertEquals(0, computer.outputs[0])

        computer.reset()

        computer.addInput(4)
        computer.run()
        assertEquals(1, computer.outputs[0])
    }

    @Test
    fun day5Complex5() {
        val computer = IntCodeComputer(intArrayOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9))

        computer.addInput(0)
        computer.run()
        assertEquals(0, computer.outputs[0])

        computer.reset()

        computer.addInput(10)
        computer.run()
        assertEquals(1, computer.outputs[0])

        computer.reset()

        computer.addInput(2)
        computer.run()
        assertEquals(1, computer.outputs[0])
    }

    @Test
    fun day5Complex6() {
        val computer = IntCodeComputer(intArrayOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1))

        computer.addInput(0)
        computer.run()
        assertEquals(0, computer.outputs[0])

        computer.reset()

        computer.addInput(10)
        computer.run()
        assertEquals(1, computer.outputs[0])

        computer.reset()

        computer.addInput(2)
        computer.run()
        assertEquals(1, computer.outputs[0])
    }

    @Test
    fun day5Complex7() {
        val computer = IntCodeComputer(
            intArrayOf(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
            )
        )

        computer.addInput(4)
        computer.run()
        assertEquals(999, computer.outputs[0])

        computer.reset()

        computer.addInput(8)
        computer.run()
        assertEquals(1000, computer.outputs[0])

        computer.reset()

        computer.addInput(10)
        computer.run()
        assertEquals(1001, computer.outputs[0])
    }
}