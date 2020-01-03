package day18.vaultGraph

import day18.vault.Key
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals


class VaultGraphTest {
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

    private val graphFactories = hashMapOf(
        "1" to { textMap: String -> VaultGraph1(textMap) },
        "2" to { textMap: String -> VaultGraph2(textMap) }
    )

    @TestFactory
    fun getAvailableKeysFrom() = testCases.flatMap { testCase ->
        graphFactories.map {
            DynamicTest.dynamicTest(testCase.name + " - " + it.key) {
                val graph = it.value(testCase.getGraph())

                val keys = graph.getAvailableKeyDistancesFrom(testCase.getKey(), testCase.getKeys())

                assertEquals(testCase.getExpected(), keys)
            }
        }
    }

    @TestFactory
    fun getDistancesToKeysFrom() = graphFactories.map {
        DynamicTest.dynamicTest("factory ${it.key}") {
            val graph = it.value(
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
    fun getMaxDistanceToKey() = graphFactories.map {
        DynamicTest.dynamicTest("factory ${it.key}") {
            val graph = it.value(
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
}
