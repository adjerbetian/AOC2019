package day12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class JupiterSystemTest {
    var system = JupiterSystem(emptyList())

    @BeforeEach
    fun setup() {
        system = JupiterSystem(
            listOf(
                Moon(Position(-1, 0, 2)),
                Moon(Position(2, -10, -7)),
                Moon(Position(4, -8, 8)),
                Moon(Position(3, 5, -1))
            )
        )
    }

    @Test
    fun step() {
        assertEquals(
            """
                pos=<x=-1, y=0, z=2>, vel=<x=0, y=0, z=0>
                pos=<x=2, y=-10, z=-7>, vel=<x=0, y=0, z=0>
                pos=<x=4, y=-8, z=8>, vel=<x=0, y=0, z=0>
                pos=<x=3, y=5, z=-1>, vel=<x=0, y=0, z=0>
            """.trimIndent(), system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=2, y=-1, z=1>, vel=<x=3, y=-1, z=-1>
                pos=<x=3, y=-7, z=-4>, vel=<x=1, y=3, z=3>
                pos=<x=1, y=-7, z=5>, vel=<x=-3, y=1, z=-3>
                pos=<x=2, y=2, z=0>, vel=<x=-1, y=-3, z=1>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=5, y=-3, z=-1>, vel=<x=3, y=-2, z=-2>
                pos=<x=1, y=-2, z=2>, vel=<x=-2, y=5, z=6>
                pos=<x=1, y=-4, z=-1>, vel=<x=0, y=3, z=-6>
                pos=<x=1, y=-4, z=2>, vel=<x=-1, y=-6, z=2>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=5, y=-6, z=-1>, vel=<x=0, y=-3, z=0>
                pos=<x=0, y=0, z=6>, vel=<x=-1, y=2, z=4>
                pos=<x=2, y=1, z=-5>, vel=<x=1, y=5, z=-4>
                pos=<x=1, y=-8, z=2>, vel=<x=0, y=-4, z=0>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=2, y=-8, z=0>, vel=<x=-3, y=-2, z=1>
                pos=<x=2, y=1, z=7>, vel=<x=2, y=1, z=1>
                pos=<x=2, y=3, z=-6>, vel=<x=0, y=2, z=-1>
                pos=<x=2, y=-9, z=1>, vel=<x=1, y=-1, z=-1>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=-1, y=-9, z=2>, vel=<x=-3, y=-1, z=2>
                pos=<x=4, y=1, z=5>, vel=<x=2, y=0, z=-2>
                pos=<x=2, y=2, z=-4>, vel=<x=0, y=-1, z=2>
                pos=<x=3, y=-7, z=-1>, vel=<x=1, y=2, z=-2>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=-1, y=-7, z=3>, vel=<x=0, y=2, z=1>
                pos=<x=3, y=0, z=0>, vel=<x=-1, y=-1, z=-5>
                pos=<x=3, y=-2, z=1>, vel=<x=1, y=-4, z=5>
                pos=<x=3, y=-4, z=-2>, vel=<x=0, y=3, z=-1>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=2, y=-2, z=1>, vel=<x=3, y=5, z=-2>
                pos=<x=1, y=-4, z=-4>, vel=<x=-2, y=-4, z=-4>
                pos=<x=3, y=-7, z=5>, vel=<x=0, y=-5, z=4>
                pos=<x=2, y=0, z=0>, vel=<x=-1, y=4, z=2>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=5, y=2, z=-2>, vel=<x=3, y=4, z=-3>
                pos=<x=2, y=-7, z=-5>, vel=<x=1, y=-3, z=-1>
                pos=<x=0, y=-9, z=6>, vel=<x=-3, y=-2, z=1>
                pos=<x=1, y=1, z=3>, vel=<x=-1, y=1, z=3>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=5, y=3, z=-4>, vel=<x=0, y=1, z=-2>
                pos=<x=2, y=-9, z=-3>, vel=<x=0, y=-2, z=2>
                pos=<x=0, y=-8, z=4>, vel=<x=0, y=1, z=-2>
                pos=<x=1, y=1, z=5>, vel=<x=0, y=0, z=2>
                """.trimIndent(),
            system.toString()
        )

        system.step()
        assertEquals(
            """
                pos=<x=2, y=1, z=-3>, vel=<x=-3, y=-2, z=1>
                pos=<x=1, y=-8, z=0>, vel=<x=-1, y=1, z=3>
                pos=<x=3, y=-6, z=1>, vel=<x=3, y=2, z=-3>
                pos=<x=2, y=0, z=4>, vel=<x=1, y=-1, z=-1>
                """.trimIndent(),
            system.toString()
        )
    }

    @Test
    fun energy() {
        repeat(10) { system.step() }

        assertEquals(179, system.getTotalEnergy())
    }
}