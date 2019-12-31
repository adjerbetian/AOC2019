package day18.vaultGraph

import day18.vault.Door
import day18.vault.Key
import day18.vault.Position
import day18.vault.Vault

class VaultGraph(private val vault: Vault) {
    val keys = vault.keys
    private val keysFromKey = HashMap<Key, KeyGraphNode>()

    constructor(textMap: String) : this(Vault(textMap))

    init {
        vault.keys.forEach { key ->
            keysFromKey[key] = buildKeyGraph(listOf(vault[key]), emptyList(), 0).first()
        }
        keysFromKey[Key('@')] = buildKeyGraphFromEntrance()
        keysFromKey.values.forEach {
            val keys = it.getKeys().sortedBy { k -> k.letter }
            if (keys.toSet().size != keys.size)
                throw Error("incorrect for ${it.key}")
        }
    }

    private fun buildKeyGraph(
        path: List<Position>,
        doors: List<Door>,
        distance: Int
    ): List<KeyGraphNode> {
        val last = vault[path.last()]

        val newDoors = when (last) {
            is Door -> doors + last
            else -> doors
        }

        val neighbours = vault.getNeighbors(path.last()).filter { !path.contains(it) }

        return if (last is Key) {
            return listOf(
                KeyGraphNode(
                    last,
                    distance,
                    doors,
                    neighbours.flatMap { buildKeyGraph(path + it, emptyList(), distance + 1) })
            )
        } else {
            neighbours.flatMap { buildKeyGraph(path + it, newDoors, distance + 1) }
        }
    }

    private fun buildKeyGraphFromEntrance(): KeyGraphNode {
        val edges = mutableListOf<KeyGraphNode>()
        val explored = mutableListOf<Position>()
        var boundary = listOf(vault[Key('@')])
        var distance = 0

        while (boundary.isNotEmpty()) {
            distance++

            explored.addAll(boundary)
            boundary = boundary
                .flatMap { vault.getNeighbors(it) }
                .asSequence()
                .filter { vault[it] !is Door }
                .filter { !explored.contains(it) }
                .toSet()
                .toList()

            edges.addAll(
                boundary.map { vault[it] }
                    .asSequence()
                    .filterIsInstance<Key>()
                    .map { KeyGraphNode(it, distance) }
            )

            boundary = boundary.filter { vault[it] !is Key }
        }
        return KeyGraphNode(Key('@'), 0, emptyList(), edges)
    }

    fun getAvailableKeysFrom(key: Key, keys: List<Key>): List<KeyDistance> {
        return keysFromKey.getValue(key).getAccessibleKeyDistances(keys).sortedBy { it.distance }
    }

    fun getKeyNodes() = keysFromKey.values

    fun getMaxDistance(key: Key, keys: List<Key>) = keysFromKey.getValue(key).getMaxDistance(keys)
}

