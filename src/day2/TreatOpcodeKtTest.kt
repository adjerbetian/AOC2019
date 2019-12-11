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
}