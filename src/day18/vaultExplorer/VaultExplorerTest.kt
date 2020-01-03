package day18.vaultExplorer

import day18.vault.Key
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals


class VaultExplorerTest {
    private val factories = listOf(
        Factory("DFS") { map: String -> VaultExplorerDFS(map) }
    )

    private val testCases = listOf(
        TestCase(
            "simple one",
            """
                #########
                #b.A.@.a#
                #########
            """,
            8,
            "a, b"
        ),
        TestCase(
            "simple one",
            """
                ########################
                #f.D.E.e.C.b.A.@.a.B.c.#
                ######################.#
                #d.....................#
                ########################
            """,
            86,
            "a, b, c, d, e, f"
        ),
        TestCase(
            "simple one",
            """
                ########################
                #...............b.C.D.f#
                #.######################
                #.....@.a.B.c.d.A.e.F.g#
                ########################
            """,
            132,
            "b, a, c, d, f, e, g"
        ),
        TestCase(
            "simple one",
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
            """,
            136,
            "a, f, b, j, g, n, h, d, l, o, e, p, c, i, k, m",
            true
        ),
        TestCase(
            "simple one",
            """
                ########################
                #@..............ac.GI.b#
                ###d#e#f################
                ###A#B#C################
                ###g#h#i################
                ########################
            """,
            81,
            "a, c, f, i, d, g, b, e, h"
        )
    )

    @TestFactory
    fun getBestKeyPath() = testCases.filter { !it.skip }.flatMap { test ->
        factories.map { factory ->
            DynamicTest.dynamicTest(test.name + " - " + factory.name) {
                val explorer = factory.function(test.getGraph())

                assertEquals(test.getExpected(), explorer.getBestKeyPath())
            }
        }
    }

    data class TestCase(
        val name: String,
        private val graph: String,
        private val distance: Int,
        private val path: String,
        val skip: Boolean = false
    ) {
        fun getGraph() = graph.trimIndent()
        fun getExpected() = Pair(keysOf(path), distance)

        private fun keysOf(keys: String) =
            keys.split(", ")
                .map { it[0] }
                .map { Key(it) }
    }

    data class Factory(val name: String, val function: (map: String) -> VaultExplorer)
}
