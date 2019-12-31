package day18

data class KeyDistance(val key: Key, val distance: Int) {
    override fun toString() = "$key: $distance"
}


data class KeyGraphNode(
    val key: Key,
    val distance: Int = 0,
    val doors: List<Door> = emptyList(),
    val edges: List<KeyGraphNode> = emptyList()
) {
    fun getAccessibleKeyDistances(keys: List<Key>): List<KeyDistance> {
        val result = mutableListOf<KeyDistance>()
        getAccessibleKeyDistances(keys, result)
        return result
    }

    private fun getAccessibleKeyDistances(keys: List<Key>, result: MutableList<KeyDistance>) {
        if (!keys.containsAll(doors.map { it.getKey() })) return

        if (!keys.contains(key)) {
            result.add(KeyDistance(key, distance))
        } else {
            edges.forEach { it.getAccessibleKeyDistances(keys, result) }
        }
    }

    fun getDistances(): List<Int> = edges.flatMap { listOf(it.distance) + it.getDistances() }

    fun getMaxDistance(keys: List<Key>): Int {
        return getAllKeyDistances().filter { !keys.contains(it.key) }.map { it.distance }.max()!!
    }

    private fun getAllKeyDistances(): List<KeyDistance> {
        val result = mutableListOf<KeyDistance>()
        getAllKeyDistances(result)
        return result
    }

    private fun getAllKeyDistances(result: MutableList<KeyDistance>) {
        result.add(KeyDistance(key, distance))
        edges.forEach { it.getAllKeyDistances(result) }
    }
}

class Vault(textMap: String) {
    private val progressPrinter = ProgressPrinter(this)
    private val map = parseKeyMap(textMap)
    var bestPathLength = Int.MAX_VALUE
    private var bestPath: List<Key> = emptyList()
    private val keysFromKey = HashMap<Key, KeyGraphNode>()
    private val summedDistances: List<Int>
    private val allKeys = map.values.filterIsInstance<Key>()
    private val keyPositions = HashMap<Key, Position>()

    init {
        allKeys.forEach { keyPositions[it] = getKeyPosition(it) }
        allKeys.forEach { key ->
            keysFromKey[key] = buildKeyGraph(listOf(getKeyPosition(key)), emptyList(), 0).first()
        }

        summedDistances = keysFromKey.values
            .flatMap { it.getDistances() }
            .sorted()
            .subList(0, allKeys.size)
            .fold(listOf(0)) { sum, distance -> sum + (sum.last() + distance) }
    }

    private fun buildKeyGraph(
        path: List<Position>,
        doors: List<Door>,
        distance: Int
    ): List<KeyGraphNode> {
        val last = map[path.last()]!!

        val newDoors = when (last) {
            is Door -> doors + last
            else -> doors
        }

        val neighbours = path.last().getNeighbors().filter { isAccessible(it) }.filter { !path.contains(it) }

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

    fun getAvailableKeysFrom(key: Key, keys: List<Key>): List<KeyDistance> {
        return keysFromKey[key]!!.getAccessibleKeyDistances(keys).sortedBy { it.distance }
    }

    private fun isAccessible(position: Position): Boolean {
        val element = map[position] ?: return false
        return when (element) {
            Wall -> false
            else -> true
        }
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

        if (keyPath.size == allKeys.size) {
            bestPathLength = pathLength
            bestPath = keyPath.map { it }
            println("\nfound key path of length $bestPathLength : $bestPath")
        }

        if (pathLength + summedDistances[allKeys.size - keyPath.size] >= bestPathLength) {
            progressPrinter.trackProgress("Min D")
            return
        }

        val maxD = keysFromKey[keyPath.last()]!!.getMaxDistance(keyPath)
        if (pathLength + maxD >= bestPathLength) {
            progressPrinter.trackProgress("Max D")
            return
        }

        val newKeys = getAvailableKeysFrom(keyPath.last(), keyPath)

        if(newKeys.map { it.key }.toSet().size != newKeys.size) throw Error("not possible")

        newKeys.forEach {
            keyPath.add(it.key)
            explorePossibleKeyPaths(
                keyPath,
                pathLength + it.distance
            )
            keyPath.removeAt(keyPath.lastIndex)
        }
    }

    fun getKeyPosition(key: Key) = map.entries.find { it.value == key }!!.key
}
