package day18.vaultGraph

import day18.vault.*

class VaultGraph1(private val vault: Vault) : VaultGraph {
    constructor(textMap: String) : this(Vault(textMap))

    override val keys = vault.keys
    private val keysFromKey = HashMap<Key, List<Triple<Key, Int, List<Door>>>>()
    private val minDistanceBetweenKeys: Int
    private val minDistances: List<Int>

    init {
        keys.forEach { key ->
            keysFromKey[key] = explorePaths(listOf(vault[key]), emptyList())
                .filter { it.first != key }
                .sortedBy { it.second }
        }
        minDistanceBetweenKeys = keysFromKey.values.flatten().map { it.second }.min()!!
        val mutableMinDistancesList =
            (listOf(0) + keysFromKey.values.flatten().map { it.second }.sorted().subList(
                0,
                keys.size
            )).toMutableList()
        for (i in 1 until mutableMinDistancesList.size) mutableMinDistancesList[i] += mutableMinDistancesList[i - 1]
        minDistances = mutableMinDistancesList
    }

    private fun explorePaths(
        path: List<Position>,
        doors: List<Door>
    ): List<Triple<Key, Int, List<Door>>> {
        val last = vault[path.last()]
        val newDoors = when (last) {
            is Door -> doors + last
            else -> doors
        }

        val neighbours = vault.getNeighbors(path.last()).filter { !path.contains(it) }

        return if (last is Key) {
            neighbours.flatMap { explorePaths(path + it, newDoors) } + Triple(last, path.size - 1, doors)
        } else {
            neighbours.flatMap { explorePaths(path + it, newDoors) }
        }
    }

    private fun getAvailableKeysFromEntrance(position: Position, keys: Set<Key>): List<KeyDistance> {
        val newKeys = mutableListOf<KeyDistance>()
        val explored = mutableListOf<Position>()
        var boundary = listOf(position)
        var distance = 0

        while (boundary.isNotEmpty()) {
            distance++

            explored.addAll(boundary)
            boundary = boundary
                .flatMap { vault.getNeighbors(it) }
                .filter { !explored.contains(it) }
            newKeys.addAll(
                boundary.map { vault[it] }
                    .filterIsInstance<Key>()
                    .filter { !keys.contains(it) }
                    .map { KeyDistance(it, distance) }
            )
            boundary = boundary.filter { vault[it] !is Key || keys.contains(vault[it]) }
        }
        return newKeys
    }

    private fun getAvailableKeysFromKey(key: Key, keys: Set<Key>): List<KeyDistance> {
        val doors = keys.map { it.getDoor() }
        return keysFromKey[key]!!
            .filter { !keys.contains(it.first) }
            .filter { doors.containsAll(it.third) }
            .map { KeyDistance(it.first, it.second) }
    }

    override fun getAvailableKeyDistancesFrom(element: TunnelElement, keys: Set<Key>): List<KeyDistance> {
        if (element is Key)
            return getAvailableKeysFromKey(element, keys)
        if (element is Entrance)
            return getAvailableKeysFromEntrance(vault[Entrance], keys)
        throw Error("not possible")
    }

    override fun getMaxDistanceToKey(key: Key, keys: Set<Key>): Int {
        return keysFromKey[key]!!
            .filter { !keys.contains(it.first) }
            .map { it.second }
            .max()!!
    }

    override fun getDistancesToKeysFrom(element: TunnelElement): List<Int> {
        if (element !is Key) throw Error("not supported")
        return keysFromKey[element]!!.map { it.second }.sorted()
    }
}


