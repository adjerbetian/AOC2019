package day1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ComputeFuelForMassKtTest {
    @Test
    fun itShouldWorkOnTestData() {
        assertEquals(2, computeFuelForMass(12))
        assertEquals(2, computeFuelForMass(14))
        assertEquals(654, computeFuelForMass(1969))
        assertEquals(33583, computeFuelForMass(100756))
    }
}