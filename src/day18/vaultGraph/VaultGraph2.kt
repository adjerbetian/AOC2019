package day18.vaultGraph

import day18.vault.*

open class Node(val element: TunnelElement, open val edges: List<Edge>)
class MutableNode(element: TunnelElement, override val edges: MutableList<Edge> = mutableListOf()) :
    Node(element, edges) {
    fun toNode() = Node(element, edges)
}

data class Edge(val to: Node, val distance: Int)

class VaultGraph2(private val vault: Vault) {
    val size = vault.getNumberOfElements()
    private val nodes: Map<TunnelElement, Node>

    init {
        val mutableNodes = vault.elements.associateWith { MutableNode(it) }
        mutableNodes.values.forEach { node ->
            node.edges.addAll(
                getAccessibleElementsFrom(vault[node.element])
                    .map {
                        Edge(
                            mutableNodes.getValue(it.first),
                            it.second
                        )
                    }
            )
        }

        nodes = mutableNodes.mapValues { it.value.toNode() }
    }

    constructor(textMap: String) : this(Vault(textMap))

    operator fun get(element: TunnelElement) = nodes.getValue(element)

    private fun getAccessibleElementsFrom(position: Position): List<Pair<TunnelElement, Int>> {
        val result = mutableListOf<Pair<TunnelElement, Int>>()

        val explored = mutableSetOf<Position>()
        var boundary = mutableSetOf(position)
        var distance = 0

        while (boundary.isNotEmpty()) {
            distance++

            explored.addAll(boundary)
            boundary = boundary
                .flatMap { vault.getNeighbors(it) }
                .filter { !explored.contains(it) }
                .toMutableSet()

            result.addAll(
                boundary
                    .map { vault[it] }
                    .filterIsInstance<TunnelElement>()
                    .map { Pair(it, distance) }
            )

            explored.addAll(boundary.filter { vault[it] is TunnelElement })
            boundary = boundary.filter { vault[it] !is TunnelElement }.toMutableSet()
        }

        return result
    }
}

