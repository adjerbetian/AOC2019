package day1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ComputeFuelForModuleKtTest {

    @Test
    fun itShouldWorkOnTestData() {
        assertEquals(2, computeFuelForModule(14))
        assertEquals(966, computeFuelForModule(1969))
        assertEquals(50346, computeFuelForModule(100756))
    }
}