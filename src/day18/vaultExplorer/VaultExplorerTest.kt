package day18.vaultExplorer

import day18.vault.Key
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals


class VaultExplorerTest {
    private val factories = listOf(
        Factory("DFS") { map -> VaultExplorerDFS(map) },
        Factory("BFS") { map -> VaultExplorerBFS(map) }
    )

    private val testCases = listOf(
        TestCase(
            "first",
            """
                #########
                #b.A.@.a#
                #########
            """,
            8,
            "a, b"
        ),
        TestCase(
            "second",
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
            "third",
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
            "fourth",
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
            "b, f, g, n, c, i, e, a, k, d, l, h, m, j, o, p"
        ),
        TestCase(
            "fifth",
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
    fun getBestKeyPath() = testCases.flatMap { test ->
        factories.map { factory ->
            DynamicTest.dynamicTest(test.name + " - " + factory.name) {
                val explorer = factory.function(test.getGraph())

                val bestKeyPath = explorer.getBestKeyPath()

                assertEquals(test.getExpectedPath(), bestKeyPath.path)
                assertEquals(test.getExpectedLength(), bestKeyPath.length)
            }
        }
    }

    data class TestCase(
        val name: String,
        private val graph: String,
        private val distance: Int,
        private val path: String
    ) {
        fun getGraph() = graph.trimIndent()
        fun getExpectedLength() = distance
        fun getExpectedPath() = keysOf(path)

        private fun keysOf(keys: String) =
            keys.split(", ")
                .map { it[0] }
                .map { Key(it) }
    }

    data class Factory(val name: String, val function: (map: String) -> VaultExplorer)
}
