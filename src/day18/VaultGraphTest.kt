package day18

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class VaultGraphTest {
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

    @Test
    fun getBestKeyPath() {
        val vault = VaultGraph(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        assertEquals(
            Pair(
                keysOf("@, a, b"),
                8
            ),
            vault.getBestKeyPath()
        )
    }

    @Test
    fun getBestKeyPath2() {
        val vault = VaultGraph(
            """
                ########################
                #f.D.E.e.C.b.A.@.a.B.c.#
                ######################.#
                #d.....................#
                ########################
            """.trimIndent()
        )

        assertEquals(
            Pair(
                keysOf("@, a, b, c, d, e, f"),
                86
            ),
            vault.getBestKeyPath()
        )
    }

    @Test
    fun getBestKeyPath3() {
        val vault = VaultGraph(
            """
                ########################
                #...............b.C.D.f#
                #.######################
                #.....@.a.B.c.d.A.e.F.g#
                ########################
            """.trimIndent()
        )

        assertEquals(
            Pair(
                keysOf("@, b, a, c, d, f, e, g"),
                132
            ),
            vault.getBestKeyPath()
        )
    }

    @Disabled
    @Test
    fun getBestKeyPath4() {
        val vault = VaultGraph(
            """
                #################
                #i.G..c...e..H.p#
                ########.########
                #j.A..b...f..D.o#
                ########@########
                #k.E..a...g..B.n#
                ########.########
                #l.F..d...h..C.m#
                #################
            """.trimIndent()
        )

        assertEquals(
            Pair(
                keysOf("@, a, f, b, j, g, n, h, d, l, o, e, p, c, i, k, m"),
                136
            ),
            vault.getBestKeyPath()
        )
    }

    @Test
    fun getBestKeyPath5() {
        val vault = VaultGraph(
            """
                ########################
                #@..............ac.GI.b#
                ###d#e#f################
                ###A#B#C################
                ###g#h#i################
                ########################
            """.trimIndent()
        )

        assertEquals(
            Pair(
                keysOf("@, a, c, f, i, d, g, b, e, h"),
                81
            ),
            vault.getBestKeyPath()
        )
    }

    private fun keysOf(keys: String) = keys.split(", ").map { it[0] }.map { Key(it) }
}
