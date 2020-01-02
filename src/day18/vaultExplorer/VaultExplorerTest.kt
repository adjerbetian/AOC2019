package day18.vaultExplorer

import day18.vault.Key
import day18.vaultGraph.VaultGraph1
import day18.vaultGraph.VaultGraph2
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class VaultExplorerTest {
    @Test
    fun getBestKeyPath() {
        val vault = VaultExplorer(
            """
                #########
                #b.A.@.a#
                #########
            """.trimIndent()
        )

        assertEquals(
            Pair(
                keysOf("a, b"),
                8
            ),
            vault.getBestKeyPath()
        )
    }

    @Test
    fun getBestKeyPath2() {
        val vault = VaultExplorer(
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
                keysOf("a, b, c, d, e, f"),
                86
            ),
            vault.getBestKeyPath()
        )
    }

    @Test
    fun getBestKeyPath3() {
        val vault = VaultExplorer(
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
                keysOf("b, a, c, d, f, e, g"),
                132
            ),
            vault.getBestKeyPath()
        )
    }

    @Disabled
    @Test
    fun getBestKeyPath4() {
        val vault = VaultExplorer(
            VaultGraph2(
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
        val vault = VaultExplorer(
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
                keysOf("a, c, f, i, d, g, b, e, h"),
                81
            ),
            vault.getBestKeyPath()
        )
    }

    private fun keysOf(keys: String) = keys.split(", ").map { it[0] }.map { Key(it) }
}
