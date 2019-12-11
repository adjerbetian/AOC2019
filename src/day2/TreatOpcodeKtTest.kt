package day2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TreatOpcodeKtTest {
    @Test
    fun simpleAddition() {
        val program = intArrayOf(1, 0, 0, 0, 99);

        treatOpcode(program, 0)

        assertEquals(intArrayOf(2, 0, 0, 0, 99).joinToString(), program.joinToString())
    }

    @Test
    fun simpleMultiplication() {
        val program = intArrayOf(2, 3, 0, 3, 99);

        treatOpcode(program, 0)

        assertEquals(intArrayOf(2, 3, 0, 6, 99).joinToString(), program.joinToString())
    }

    @Test
    fun simpleCombination() {
        val program = intArrayOf(2, 4, 4, 5, 99, 0);

        treatOpcode(program, 0)

        assertEquals(intArrayOf(2, 4, 4, 5, 99, 9801).joinToString(), program.joinToString())
    }

    @Test
    fun complexCombination() {
        val program = intArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99);

        treatOpcode(program, 0)

        assertEquals(intArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99).joinToString(), program.joinToString())
    }
}