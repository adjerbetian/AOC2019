package day18.vaultGraph

import day18.vault.Key
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VaultGraphTest {
    @Test
    fun getAvailableKeysFrom() {
        val graph = VaultGraph(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        val keys = graph.getAvailableKeysFrom(
            Key('@'),
            listOf(Key('@'))
        )

        assertEquals(
            listOf(
                KeyDistance(Key('a'), 2)
            ),
            keys
        )
    }

    @Test
    fun getAvailableKeysFrom2() {
        val graph = VaultGraph(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        val keys = graph.getAvailableKeysFrom(
            Key('a'),
            listOf(Key('@'), Key('a'))
        )

        assertEquals(
            listOf(
                KeyDistance(Key('b'), 6)
            ),
            keys
        )
    }

    @Test
    fun getAvailableKeysFrom3() {
        val vault = VaultGraph(
            """
                #########
                #@.a.b.c#
                #########
            """.trimIndent()
        )

        val keys = vault.getAvailableKeysFrom(
            Key('@'),
            listOf(Key('@'))
        )

        assertEquals(
            listOf(
                KeyDistance(Key('a'), 2)
            ),
            keys
        )
    }

    @Test
    fun getAvailableKeysFrom4() {
        val vault = VaultGraph(
            """
                #########
                #a.b.c.@#
                #########
            """.trimIndent()
        )

        val keys = vault.getAvailableKeysFrom(
            Key('a'),
            listOf(Key('a'), Key('b'))
        )

        assertEquals(
            listOf(
                KeyDistance(Key('c'), 4)
            ),
            keys
        )
    }
}
