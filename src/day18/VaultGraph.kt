package day18

class VaultGraph(private val vault: Vault) {
    private val progressPrinter = ProgressPrinter(this)
    var bestPathLength = Int.MAX_VALUE
    private var bestPath: List<Key> = emptyList()
    private val keysFromKey = HashMap<Key, KeyGraphNode>()
    private val summedDistances: List<Int>

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


        summedDistances = keysFromKey.values
            .flatMap { it.getDistances() }
            .sorted()
            .subList(0, vault.keys.size)
            .fold(listOf(0)) { sum, distance -> sum + (sum.last() + distance) }
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
        return keysFromKey[key]!!.getAccessibleKeyDistances(keys).sortedBy { it.distance }
    }

    fun getBestKeyPath(): Pair<List<Key>, Int> {
        bestPathLength = Int.MAX_VALUE
        explorePossibleKeyPaths(mutableListOf(Key('@')), 0)
        return Pair(bestPath, bestPathLength)
    }

    private fun explorePossibleKeyPaths(keyPath: MutableList<Key>, pathLength: Int) {
        if (pathLength >= bestPathLength) {
            progressPrinter.trackProgress("Path")
            return
        }

        if (keyPath.size == vault.keys.size) {
            bestPathLength = pathLength
            bestPath = keyPath.map { it }
            println("\nfound key path of length $bestPathLength : $bestPath")
        }

        if (pathLength + summedDistances[vault.keys.size - keyPath.size] >= bestPathLength) {
            progressPrinter.trackProgress("Min D")
            return
        }

        val maxD = keysFromKey[keyPath.last()]!!.getMaxDistance(keyPath)
        if (pathLength + maxD >= bestPathLength) {
            progressPrinter.trackProgress("Max D")
            return
        }

        val newKeys = getAvailableKeysFrom(keyPath.last(), keyPath)

        if (newKeys.map { it.key }.toSet().size != newKeys.size) throw Error("not possible")

        newKeys.forEach {
            keyPath.add(it.key)
            explorePossibleKeyPaths(
                keyPath,
                pathLength + it.distance
            )
            keyPath.removeAt(keyPath.lastIndex)
        }
    }
}