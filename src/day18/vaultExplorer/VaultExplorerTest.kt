package day18.vaultExplorer

import day18.vault.Key
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.time.Duration
import kotlin.test.assertEquals


class VaultExplorerTest {
    private val factories = listOf(
        Factory("DFS") { map -> VaultExplorerDFS(map) },
        Factory("BFS") { map -> VaultExplorerBFS(map, 100) }
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
            "a, b",
            true
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
            "b, a, c, d, f, e, g",
            true
        ),
        TestCase(
            "fourth",
            """
                ########################
                #@..............ac.GI.b#
                ###d#e#f################
                ###A#B#C################
                ###g#h#i################
                ########################
            """,
            81,
            "a, c, f, i, d, g, b, e, h",
            true
        )
    )

    @TestFactory
    fun getBestKeyPath() = testCases.filter { !it.skip }.flatMap { test ->
        factories.map { factory ->
            DynamicTest.dynamicTest(test.name + " - " + factory.name) {
                val explorer = factory.function(test.getGraph())

                assertEquals(test.getExpectedPath(), explorer.getBestKeyPath().first)
                assertEquals(test.getExpectedDistance(), explorer.getBestKeyPath().second)
            }
        }
    }

    @Disabled // works but is too long
    @Test
    fun trickyComplexCaseBFS() {
        val explorer = VaultExplorerBFS(
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
            """.trimIndent(),
            100000
        )

        assertEquals(136, explorer.getBestKeyPath().second)
    }

    @Test
    fun trickyComplexCaseDFS() {
        val explorer = VaultExplorerDFS(
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

        assertEquals(136, explorer.getBestKeyPath().second)
    }

    data class TestCase(
        val name: String,
        private val graph: String,
        private val distance: Int,
        private val path: String,
        val skip: Boolean = false
    ) {
        fun getGraph() = graph.trimIndent()
        fun getExpectedDistance() = distance
        fun getExpectedPath() = keysOf(path)

        private fun keysOf(keys: String) =
            keys.split(", ")
                .map { it[0] }
                .map { Key(it) }
    }

    data class Factory(val name: String, val function: (map: String) -> VaultExplorer)
}
