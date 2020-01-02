package day18.vaultGraph

import day18.vault.*

open class Node(val element: TunnelElement, open val edges: List<Edge>) {
    override fun equals(other: Any?) = other is Node && other.element == element
    override fun hashCode() = edges.hashCode()
}

class MutableNode(element: TunnelElement, override val edges: MutableList<Edge> = mutableListOf()) :
    Node(element, edges)

data class Edge(val to: Node, val distance: Int)

class VaultGraph2(private val vault: Vault) : VaultGraph {
    val size = vault.getNumberOfElements()
    override val keys = vault.keys
    private val nodes = buildNodes()
    private val distancesToKeys = nodes.keys.associateWith { getKeyDistancesFrom(it) }

    constructor(textMap: String) : this(Vault(textMap))

    private fun buildNodes(): Map<TunnelElement, Node> {
        val result = vault.elements.associateWith { MutableNode(it) }
        result.values.forEach { node ->
            node.edges.addAll(
                getAccessibleElementsFrom(vault[node.element])
                    .map {
                        Edge(
                            result.getValue(it.first),
                            it.second
                        )
                    }
            )
        }
        return result
    }

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

    operator fun get(element: TunnelElement) = nodes.getValue(element)

    override fun getAvailableKeyDistancesFrom(element: TunnelElement, keys: List<Key>): List<KeyDistance> {
        val distances = HashMap<TunnelElement, Int>()

        fun explore(node: Node, distance: Int) {
            if (distances[node.element] ?: Int.MAX_VALUE <= distance) return
            distances[node.element] = distance

            if (node.element is Key && !keys.contains(node.element)) return
            if (node.element is Door && node.element.isLocked(keys)) return
            node.edges.forEach { explore(it.to, distance + it.distance) }
        }
        explore(this[element], 0)

        return distances.keys
            .asSequence()
            .filter { it != element }
            .filterIsInstance<Key>()
            .filter { !keys.contains(it) }
            .map { KeyDistance(it, distances[it]!!) }
            .sortedBy { it.distance }
            .toList()
    }

    private fun getKeyDistancesFrom(element: TunnelElement): List<KeyDistance> {
        val distances = HashMap<TunnelElement, Int>()

        fun explore(node: Node, distance: Int) {
            if (distances[node.element] ?: Int.MAX_VALUE <= distance) return
            distances[node.element] = distance
            node.edges.forEach { explore(it.to, distance + it.distance) }
        }
        explore(this[element], 0)

        return distances.keys
            .filter { it != element }
            .filterIsInstance<Key>()
            .map { KeyDistance(it, distances[it]!!) }
            .sortedBy { it.distance }
    }

    override fun getMaxDistanceToKey(key: Key, keys: List<Key>): Int {
        return distancesToKeys.getValue(key)
            .filter { !keys.contains(it.key) }
            .map { it.distance }
            .max()!!
    }

    override fun getDistancesToKeysFrom(element: TunnelElement): List<Int> {
        return distancesToKeys.getValue(element).map { it.distance }
    }
}

