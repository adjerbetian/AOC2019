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
                vault.elements
                    .filter { it != node.element }
                    .map { to ->
                        Edge(
                            mutableNodes.values.find { it.element == to }!!,
                            2
                        )
                    }
            )
        }

        nodes = mutableNodes.mapValues { it.value.toNode() }
    }

    constructor(textMap: String) : this(Vault(textMap))

    operator fun get(element: TunnelElement) = nodes.getValue(element)
}

