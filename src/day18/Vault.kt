package day18

data class KeyDistance(val key: Key, val distance: Int)

class Vault(textMap: String) {
    private val map = parseKeyMap(textMap)
    private var bestPathLength = Int.MAX_VALUE
    private var bestPath: List<Key> = emptyList()
    private val keysFromKey = HashMap<Key, List<Triple<Key, Int, List<Door>>>>()
    private val minDistanceBetweenKeys: Int
    private val allKeys = getAllKeys()
    private val keyPositions = HashMap<Key, Position>()
    var i = 0

    init {
        allKeys.forEach { keyPositions[it] = getKeyPosition(it) }
        allKeys.forEach { key ->
            keysFromKey[key] = explorePaths(listOf(keyPositions[key]!!), emptyList())
                .filter { it.first != key }
                .sortedBy { it.second }
        }
        minDistanceBetweenKeys = keysFromKey.values.flatten().map { it.second }.min()!!
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
            .sortedBy { it.distance }
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
        explorePossibleKeyPaths(emptyList(), 0)
        return Pair(bestPath, bestPathLength)
    }

    private fun explorePossibleKeyPaths(keyPath: List<Key>, pathLength: Int) {
        if (pathLength >= bestPathLength) {
            i++
            if (i % 10000000 == 0) println()
            if (i % 100000 == 0) print(".")
            return
        }

        if (pathLength + (allKeys.size - keyPath.size) * minDistanceBetweenKeys >= bestPathLength) {
            i++
            if (i % 10000000 == 0) println()
            if (i % 100000 == 0) print("X")
            return
        }

        if (keyPath.size == allKeys.size) {
            bestPathLength = pathLength
            bestPath = keyPath
            println("\nfound key path of length $bestPathLength : $bestPath")
        }

        val position = if (keyPath.isEmpty())
            getInitialPosition()
        else
            keyPositions[keyPath.last()]!!

        val newKeys = getAvailableKeysFrom(position, keyPath)
        newKeys.forEach {
            explorePossibleKeyPaths(
                keyPath + it.key,
                pathLength + it.distance
            )
        }
    }

    fun getKeyPosition(key: Key) = map.entries.find { it.value == key }!!.key
}

fun parseKeyMap(textMap: String): HashMap<Position, MapElement> {
    val result = HashMap<Position, MapElement>()

    var x = 0
    var y = 0
    for (c in textMap) {
        if (c == '\n') {
            x = 0
            y++
        } else {
            result[Position(x++, y)] = when {
                c == '.' -> OpenPassage
                c == '@' -> Entrance
                c == '#' -> Wall
                c.isLetter() && c.isLowerCase() -> Key(c)
                c.isLetter() && c.isUpperCase() -> Door(c)
                else -> throw Error("character not recognized: $c")
            }
        }
    }

    return result
}