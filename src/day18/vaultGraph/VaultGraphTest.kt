package day18.vaultGraph

import day18.vault.Entrance
import day18.vault.Key
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VaultGraphTest {
    @Test
    fun buildSimpleGraph() {
        val graph = VaultGraph2(
            """
                #####
                #@.a#
                #####
            """.trimIndent()
        )

        assertEquals(2, graph.size)

        assertEquals(1, graph[Entrance].edges.size)
        assertEquals(1, graph[Key('a')].edges.size)

        assertEquals(Key('a'), graph[Entrance].edges[0].to.element)
        assertEquals(Entrance, graph[Key('a')].edges[0].to.element)

        assertEquals(Entrance, graph[Entrance].edges[0].to.edges[0].to.element)
        assertEquals(Key('a'), graph[Key('a')].edges[0].to.edges[0].to.element)
    }

    @Test
    fun simpleDistance() {
        val graph = VaultGraph2(
            """
                #####
                #@.a#
                #####
            """.trimIndent()
        )

        assertEquals(2, graph[Entrance].edges[0].distance)
    }

    @Test
    fun linearGraph() {
        val graph = VaultGraph2(
            """
                #########
                #a..@..b#
                #########
            """.trimIndent()
        )

        assertEquals(2, graph[Entrance].edges.size)
        assertEquals(3, graph[Entrance].edges[0].distance)
        assertEquals(3, graph[Entrance].edges[1].distance)
        assertEquals(listOf('a', 'b'), graph[Entrance].edges.map { it.to.element.letter }.sorted())

        assertEquals(1, graph[Key('a')].edges.size)
        assertEquals(3, graph[Key('a')].edges[0].distance)
        assertEquals(Entrance, graph[Key('a')].edges[0].to.element)
    }

    @Test
    fun linearGraphWithSortedDistances() {
        assertEquals(
            'b',
            VaultGraph2(
                """
                    ##########
                    #a...@..b#
                    ##########
                """.trimIndent()
            )[Entrance].edges[0].to.element.letter
        )
        assertEquals(
            'a',
            VaultGraph2(
                """
                    ##########
                    #a..@...b#
                    ##########
                """.trimIndent()
            )[Entrance].edges[0].to.element.letter
        )
    }

    @Test
    fun linearGraphWithHiddenAccess() {
        val graph = VaultGraph2(
            """
                #########
                #@..a..b#
                #########
            """.trimIndent()
        )

        assertEquals(1, graph[Entrance].edges.size)
        assertEquals('a', graph[Entrance].edges[0].to.element.letter)
    }

    @Test
    fun complexExample() {
        val graph = VaultGraph2(
            """
                #################
                #h.A..b...c..D.g#
                ########@########
                #e.E..a...d..B.f#
                #################
            """.trimIndent()
        )

        assertEquals(
            listOf('a', 'b', 'c', 'd'),
            graph[Entrance].edges.map { it.to.element.letter }.sorted()
        )
        assertEquals(
            listOf('@', 'A', 'c'),
            graph[Key('b')].edges.map { it.to.element.letter }.sorted()
        )
    }

    @Test
    fun complexExampleWithSquareInTheMiddle() {
        val graph = VaultGraph2(
            """
                #################
                #h.A..b...c..D.g#
                #######.@.#######
                #e.E..a...d..B.f#
                #################
            """.trimIndent()
        )

        assertEquals(
            listOf('a', 'b', 'c', 'd'),
            graph[Entrance].edges.map { it.to.element.letter }.sorted()
        )
        assertEquals(
            listOf('@', 'A', 'a', 'c', 'd'),
            graph[Key('b')].edges.map { it.to.element.letter }.sorted()
        )
    }
}
