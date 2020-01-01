package day18.vault

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VaultTest {
    @Test
    fun getKeyPosition() {
        val vault = Vault(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        assertEquals(Position(5, 1), vault[Entrance])
        assertEquals(Position(7, 1), vault[Key('a')])
        assertEquals(Position(1, 1), vault[Key('b')])
    }

    @Test
    fun get() {
        val vault = Vault(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        assertEquals(Wall, vault[Position(0, 0)])
        assertEquals(Key('b'), vault[Position(1, 1)])
        assertEquals(Door('A'), vault[Position(3, 1)])
    }

    @Test
    fun getNeighbors() {
        val vault = Vault(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        assertEquals(
            listOf(Position(2, 1)),
            vault.getNeighbors(vault[Key('b')])
        )
        assertEquals(
            listOf(Position(2, 1), Position(4, 1)),
            vault.getNeighbors(vault[Door('A')])
        )
        assertEquals(
            listOf(Position(3, 1), Position(5, 1)),
            vault.getNeighbors(Position(4, 1))
        )
    }
}
