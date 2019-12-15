package day6

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

internal class OrbitSystemTest {
    @Test
    fun getCenter() {
        val map = listOf(
            "COM)A",
            "A)B",
            "B)C"
        )

        val system = OrbitSystem(map)

        assertEquals("A", system.getOrbitCenterOf("B"))
        assertEquals("B", system.getOrbitCenterOf("C"))
    }

    @Test
    fun distanceToCenterOfMass() {
        val map = listOf(
            "COM)A",
            "A)B",
            "B)C",
            "C)D",
            "D)E"
        )

        val system = OrbitSystem(map)

        assertEquals(1, system.getDistanceToCOM("A"))
        assertEquals(2, system.getDistanceToCOM("B"))
        assertEquals(5, system.getDistanceToCOM("E"))
    }

    @Test
    fun orbitsSum() {
        val map = listOf(
            "COM)B",
            "B)C",
            "C)D",
            "D)E",
            "E)F",
            "B)G",
            "G)H",
            "D)I",
            "E)J",
            "J)K",
            "K)L"
        )

        val system = OrbitSystem(map)

        assertEquals(42, system.getOrbitsSum())
    }
}
