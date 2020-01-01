package day18.vaultGraph

import day18.vault.Key
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VaultGraph2Test {
    @Test
    fun getAvailableKeysFrom() {
        val graph = VaultGraph2(
            """
                #########
                #b...@.a#
                #########
            """.trimIndent()
        )

        val keys = graph.getAvailableKeyDistancesFrom(
            Key('a'),
            listOf(Key('a'))
        )

        assertEquals(
            listOf(
                KeyDistance(Key('b'), 6)
            ),
            keys
        )
    }

    @Test
    fun getAvailableKeysFrom2() {
        val graph = VaultGraph2(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        val keys = graph.getAvailableKeyDistancesFrom(
            Key('a'),
            listOf(Key('a'))
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
        val vault = VaultGraph2(
            """
                #########
                #@.a.b.c#
                #########
            """.trimIndent()
        )

        val keys = vault.getAvailableKeyDistancesFrom(
            Key('a'),
            listOf(Key('a'))
        )

        assertEquals(
            listOf(
                KeyDistance(Key('b'), 2)
            ),
            keys
        )
    }

    @Test
    fun getAvailableKeysFrom4() {
        val vault = VaultGraph2(
            """
                #########
                #@.a.b.c#
                #########
            """.trimIndent()
        )

        val keys = vault.getAvailableKeyDistancesFrom(
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

    @Test
    fun getAvailableKeysFrom5() {
        val graph = VaultGraph2(
            """
                #################
                #h.A..b...c..D.g#
                #######.@.#######
                #...e..a.....B.f#
                #################
            """.trimIndent()
        )

        assertEquals(
            listOf(
                KeyDistance(Key('a'), 3),
                KeyDistance(Key('c'), 4),
                KeyDistance(Key('f'), 11)
            ),
            graph.getAvailableKeyDistancesFrom(
                Key('b'),
                listOf(Key('b'))
            )
        )
    }

    @Test
    fun getDistancesToKeysFrom() {
        val graph = VaultGraph2(
            """
                #################
                #h.A..b...c..D.g#
                #######.@.#######
                #......a..d..B.f#
                #################
            """.trimIndent()
        )

        assertEquals(
            listOf(
                3, // a
                4, // c
                5, // h
                6, // d
                9, // g
                11 // f
            ),
            graph.getDistancesToKeysFrom(Key('b'))
        )
    }

    @Test
    fun getMaxDistanceToKey() {
        val graph = VaultGraph2(
            """
                #################
                #h.A..b...c..D.g#
                #######.@.#######
                #......a..d..B.f#
                #################
            """.trimIndent()
        )

        assertEquals(
            11,
            graph.getMaxDistanceToKey(Key('b'), listOf(Key('b')))
        )
        assertEquals(
            9,
            graph.getMaxDistanceToKey(Key('b'), listOf(Key('b'), Key('f')))
        )
    }
}
