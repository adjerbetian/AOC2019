package day18.vaultGraph

import day18.vault.Key
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals


class VaultGraph2Test {
    data class TestCase(
        val name: String,
        private val graph: String,
        private val from: String,
        private val visited: String,
        private val expected: String
    ) {
        fun getGraph1() = VaultGraph1(graph.trimIndent())
        fun getGraph2() = VaultGraph2(graph.trimIndent())
        fun getKey() = Key(from)
        fun getKeys() = visited.split(", ").map { Key(it) }
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

    @TestFactory
    fun getAvailableKeysFrom() = testCases.flatMap { testCase ->
        listOf(
            DynamicTest.dynamicTest(testCase.name + " - 1") {
                val graph = testCase.getGraph1()

                val keys = graph.getAvailableKeyDistancesFrom(testCase.getKey(), testCase.getKeys())

                assertEquals(testCase.getExpected(), keys)
            },
            DynamicTest.dynamicTest(testCase.name + " - 2") {
                val graph = testCase.getGraph2()

                val keys = graph.getAvailableKeyDistancesFrom(testCase.getKey(), testCase.getKeys())

                assertEquals(testCase.getExpected(), keys)
            }
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
