package day18

data class KeyDistance(val key: Key, val distance: Int)

class Vault(textMap: String) {
    private val progressPrinter = ProgressPrinter(this)
    private val map = parseKeyMap(textMap)
    var bestPathLength = Int.MAX_VALUE
    var bestPath: List<Key> = emptyList()
    private val keysFromKey = HashMap<Key, List<Triple<Key, Int, List<Door>>>>()
    private val minDistanceBetweenKeys: Int
    private val minDistances: List<Int>
    private val allKeys = getAllKeys()
    private val keyPositions = HashMap<Key, Position>()

    init {
        allKeys.forEach { keyPositions[it] = getKeyPosition(it) }
        allKeys.forEach { key ->
            keysFromKey[key] = explorePaths(listOf(keyPositions[key]!!), emptyList())
                .filter { it.first != key }
                .sortedBy { it.second }
        }
        minDistanceBetweenKeys = keysFromKey.values.flatten().map { it.second }.min()!!
        val mutableMinDistancesList =
            (listOf(0) + keysFromKey.values.flatten().map { it.second }.sorted().subList(
                0,
                allKeys.size
            )).toMutableList()
        for (i in 1 until mutableMinDistancesList.size) mutableMinDistancesList[i] += mutableMinDistancesList[i - 1]
        minDistances = mutableMinDistancesList
    }

    private fun explorePaths(
        path: List<Position>,
        doors: List<Door>
    ): List<Triple<Key, Int, List<Door>>> {
        val last = map[path.last()]!!
        val newDoors = when (last) {
            is Door -> doors + last
            else -> doors
        }

        val neighbours = path.last().getNeighbors().filter { isAccessible(it) }.filter { !path.contains(it) }

        return if (last is Key) {
            neighbours.flatMap { explorePaths(path + it, newDoors) } + Triple(last, path.size - 1, doors)
        } else {
            neighbours.flatMap { explorePaths(path + it, newDoors) }
        }
    }

    fun getInitialPosition() = map.entries.find { it.value is Entrance }!!.key

    fun getAvailableKeysFrom(position: Position, keys: List<Key>): List<KeyDistance> {
        if (map[position] is Key) return getAvailableKeysFromKey(map[position] as Key, keys)

        val newKeys = mutableListOf<KeyDistance>()
        val explored = mutableListOf<Position>()
        var boundary = listOf(position)
        var distance = 0

        while (boundary.isNotEmpty()) {
            distance++

            explored.addAll(boundary)
            boundary = boundary
                .flatMap { it.getNeighbors() }
                .filter { isAccessible(it, keys) }
                .filter { !explored.contains(it) }
            newKeys.addAll(
                boundary.map { map[it] }
                    .filterIsInstance<Key>()
                    .filter { !keys.contains(it) }
                    .map { KeyDistance(it, distance) }
            )
            boundary = boundary.filter { map[it] !is Key || keys.contains(map[it]) }
        }
        return newKeys
    }

    private fun getAvailableKeysFromKey(key: Key, keys: List<Key>): List<KeyDistance> {
        val doors = keys.map { it.getDoor() }
        return keysFromKey[key]!!
            .filter { !keys.contains(it.first) }
            .filter { doors.containsAll(it.third) }
            .map { KeyDistance(it.first, it.second) }
    }

    private fun isAccessible(position: Position, keys: List<Key>): Boolean {
        val element = map[position] ?: return false
        return when (element) {
            Wall -> false
            Entrance -> true
            OpenPassage -> true
            is Key -> true
            is Door -> keys.any { it.opens(element) }
        }
    }

    private fun isAccessible(position: Position): Boolean {
        val element = map[position] ?: return false
        return when (element) {
            Wall -> false
            else -> true
        }
    }

    private fun getAllKeys() = map.values.filterIsInstance<Key>()

    fun getBestKeyPath(): Pair<List<Key>, Int> {
        bestPathLength = Int.MAX_VALUE
        explorePossibleKeyPaths(mutableListOf(), 0)
        return Pair(bestPath, bestPathLength)
    }

    private fun explorePossibleKeyPaths(keyPath: MutableList<Key>, pathLength: Int) {
        if (pathLength >= bestPathLength) {
            progressPrinter.trackProgress('.')
            return
        }

        if (keyPath.size == allKeys.size) {
            bestPathLength = pathLength
            bestPath = keyPath.map { it }
            println("\nfound key path of length $bestPathLength : $bestPath")
        }

        if (pathLength + minDistances[allKeys.size - keyPath.size] >= bestPathLength) {
            progressPrinter.trackProgress('*')
            return
        }

        if (keyPath.isNotEmpty()) {
            val maxD = keysFromKey[keyPath.last()]!!
                .filter { !keyPath.contains(it.first) }
                .map { it.second }
                .max()!!

            if (pathLength + maxD >= bestPathLength) {
                progressPrinter.trackProgress('X')
                return
            }
        }

        val position = if (keyPath.isEmpty())
            getInitialPosition()
        else
            keyPositions[keyPath.last()]!!

        val newKeys = getAvailableKeysFrom(position, keyPath)
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
