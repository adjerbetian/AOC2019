package day1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ComputeModuleFuelKtTest {
    @Test
    fun itShouldWorkOnTestData() {
        assertEquals(2, computeModuleFuel(12))
        assertEquals(2, computeModuleFuel(14))
        assertEquals(654, computeModuleFuel(1969))
        assertEquals(33583, computeModuleFuel(100756))
    }
}