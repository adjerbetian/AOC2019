package day18.vaultGraph

import day18.vault.Key
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals


class VaultGraphTest {
    private val testCases = listOf(
        TestCase(
            name = "simple case",
            graph = """
                #########
                #b...@.a#
                #########
            """,
            from = "a",
            visited = "a",
            expected = "b: 6"
        ),
        TestCase(
            name = "simple case with a door",
            graph = """
                #########
                #b.A.@.a#
                #########
            """,
            from = "a",
            visited = "a",
            expected = "b: 6"
        ),
        TestCase(
            name = "simple linear case",
            graph = """
                #########
                #@.a.b.c#
                #########
            """,
            from = "a",
            visited = "a",
            expected = "b: 2"
        ),
        TestCase(
            name = "simple linear case with more keys",
            graph = """
                #########
                #@.a.b.c#
                #########
            """,
            from = "a",
            visited = "a, b",
            expected = "c: 4"
        ),
        TestCase(
            name = "complex case",
            graph = """
                #################
                #h.A..b...c..D.g#
                #######.@.#######
                #...e..a.....B.f#
                #################
            """,
            from = "b",
            visited = "b",
            expected = "a: 3, c: 4, f: 11"
        )
    )

    private val factories = listOf(
        Factory("1") { textMap: String -> VaultGraph1(textMap) },
        Factory("2") { textMap: String -> VaultGraph2(textMap) }
    )

    @TestFactory
    fun getAvailableKeysFrom() = testCases.flatMap { testCase ->
        factories.map {factory ->
            DynamicTest.dynamicTest(testCase.name + " - " + factory.name) {
                val graph = factory.function(testCase.getGraph())

                val keys = graph.getAvailableKeyDistancesFrom(testCase.getKey(), testCase.getKeys())

                assertEquals(testCase.getExpected(), keys)
            }
        }
    }

    @TestFactory
    fun getDistancesToKeysFrom() = factories.map {
        DynamicTest.dynamicTest("factory ${it.name}") {
            val graph = it.function(
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
    }

    @TestFactory
    fun getMaxDistanceToKey() = factories.map {
        DynamicTest.dynamicTest("factory ${it.name}") {
            val graph = it.function(
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
                graph.getMaxDistanceToKey(Key('b'), setOf(Key('b')))
            )
            assertEquals(
                9,
                graph.getMaxDistanceToKey(Key('b'), setOf(Key('b'), Key('f')))
            )
        }
    }

    data class TestCase(
        val name: String,
        private val graph: String,
        private val from: String,
        private val visited: String,
        private val expected: String
    ) {
        fun getGraph() = graph.trimIndent()
        fun getKey() = Key(from)
        fun getKeys() = visited.split(", ").map { Key(it) }.toSet()
        fun getExpected() = expected.split(", ").map {
            KeyDistance(
                Key(it.split(": ")[0]),
                it.split(": ")[1].toInt()
            )
        }
    }

    data class Factory(val name: String, val function: (map: String) -> VaultGraph)
}
