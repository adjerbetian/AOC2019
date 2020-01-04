package day18.vaultGraph

import day18.vault.*

open class Node(val element: TunnelElement, open val edges: List<Edge>) {
    override fun equals(other: Any?) = other is Node && other.element == element
    override fun hashCode() = edges.hashCode()
}

class MutableNode(element: TunnelElement, override val edges: MutableList<Edge> = mutableListOf()) :
    Node(element, edges)

data class Edge(val to: Node, val distance: Int)

class VaultGraphImpl(private val vault: Vault) : VaultGraph {
    val size = vault.getNumberOfElements()
    override val keys = vault.keys
    private val nodes = buildNodes()
    private val precomputedKeys = nodes.keys.associateWith { buildPrecomputedKeys(it) }

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

    private fun buildPrecomputedKeys(element: TunnelElement): List<Pair<Set<Key>, KeyDistance>> {
        val result = mutableListOf<Pair<Set<Key>, KeyDistance>>()
        val visited = HashMap<TunnelElement, Pair<Int, Set<Key>>>()

        fun explore(node: Node, distance: Int, keys: Set<Key>) {
            val visitedElement = visited[node.element]
            if (visitedElement != null) {
                if (visitedElement.first <= distance && keys.containsAll(visitedElement.second)) {
                    return
                }
                if (visitedElement.first >= distance && visitedElement.second.containsAll(keys)) {
                    result.removeIf { it.second.key == node.element }
                }
            }
            visited[node.element] = Pair(distance, keys)

            if (node.element is Key)
                result.add(Pair(keys, KeyDistance(node.element, distance)))

            val newKeys = when (node.element) {
                is Door -> keys + node.element.getKey()
                is Key -> keys + node.element
                else -> keys
            }

            node.edges.forEach { explore(it.to, distance + it.distance, newKeys) }
        }
        explore(this[element], 0, emptySet())

        return result
            .filter { it.second.key != element }
            .map {
                Pair(
                    it.first
                        .filter { key -> key != element }
                        .toSet(),
                    it.second
                )
            }
            .sortedBy { it.second.distance }
    }

    operator fun get(element: TunnelElement) = nodes.getValue(element)

    override fun getAvailableKeyDistancesFrom(element: TunnelElement, keys: Set<Key>): List<KeyDistance> {
        return precomputedKeys.getValue(element)
            .filter { !keys.contains(it.second.key) }
            .filter { keys.containsAll(it.first) }
            .map { it.second }
    }

    override fun getMaxDistanceToKey(key: Key, keys: Set<Key>): Int {
        return precomputedKeys.getValue(key)
            .filter { !keys.contains(it.second.key) }
            .map { it.second.distance }
            .max() ?: 0
    }

    override fun getDistancesToKeysFrom(element: TunnelElement): List<Int> {
        return precomputedKeys.getValue(element)
            .map { it.second.distance }
    }
}

