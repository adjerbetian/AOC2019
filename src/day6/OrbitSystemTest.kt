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

    @Test
    fun getPathToCOM() {
        val map = listOf(
            "COM)A",
            "A)B",
            "B)C",
            "C)D",
            "D)E"
        )

        val system = OrbitSystem(map)

        assertArrayEquals(arrayOf("COM"), system.getPathToCOM("COM").toTypedArray())
        assertArrayEquals(arrayOf("A", "COM"), system.getPathToCOM("A").toTypedArray())
        assertArrayEquals(arrayOf("E", "D", "C", "B", "A", "COM"), system.getPathToCOM("E").toTypedArray())
    }

    @Test
    fun getFirstCommonAncestor() {
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
            "K)L",
            "K)YOU",
            "I)SAN"
        )

        val system = OrbitSystem(map)

        assertEquals("D", system.getFirstCommonAncestor("YOU", "SAN"))
    }

    @Test
    fun getPathTo() {
        val map = listOf(
            "COM)A",
            "A)B",
            "B)C",
            "C)D",
            "D)E"
        )

        val system = OrbitSystem(map)

        assertArrayEquals(arrayOf("COM"), system.getPath(from = "COM", to = "COM").toTypedArray())
        assertArrayEquals(arrayOf("A", "COM"), system.getPath(from = "A", to = "COM").toTypedArray())
        assertArrayEquals(arrayOf("E", "D", "C", "B"), system.getPath(from = "E", to = "B").toTypedArray())
    }

    @Test
    fun getOrbitalTransfers() {
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
            "K)L",
            "K)YOU",
            "I)SAN"
        )

        val system = OrbitSystem(map)

        assertEquals(6, system.getNOrbitalTransfers(from = "YOU", to = "SAN"))
    }
}
