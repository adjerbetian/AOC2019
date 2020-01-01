package day18.vaultGraph

import day18.vault.Entrance
import day18.vault.Key
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VaultGraph2Test {
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

    @Disabled
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
        assertEquals(2, graph[Entrance].edges[0].distance)
        assertEquals(2, graph[Entrance].edges[1].distance)
        assertEquals(listOf('a', 'b'), graph[Entrance].edges.map { it.to.element.letter }.sorted())

        assertEquals(1, graph[Key('a')].edges.size)
        assertEquals(2, graph[Key('a')].edges[0].distance)
        assertEquals(Entrance, graph[Key('a')].edges[0].to.element)
    }

    @Disabled
    @Test
    fun linearGraphWithSortedDistances() {
        assertEquals(
            'a',
            VaultGraph2(
                """
                    ##########
                    #a...@..b#
                    ##########
                """.trimIndent()
            )[Entrance].edges[0].to.element.letter
        )
        assertEquals(
            'b',
            VaultGraph2(
                """
                    ##########
                    #a..@...b#
                    ##########
                """.trimIndent()
            )[Entrance].edges[0].to.element.letter
        )
    }

    @Disabled
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
}
