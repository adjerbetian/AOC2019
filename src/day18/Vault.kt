package day18

sealed class MapElement
object Wall : MapElement()
object Entrance : MapElement()
object OpenPassage : MapElement()
data class Key(val letter: Char) : MapElement() {
    fun opens(door: Door) = door.letter.toLowerCase() == letter

    override fun toString() = letter.toString()
}

data class Door(val letter: Char) : MapElement() {
    override fun toString() = letter.toString()
}

data class KeyDistance(val key: Key, val distance: Int)

class Vault(textMap: String) {
    val map = parseKeyMap(textMap)
    var bestPathLength = Int.MAX_VALUE
    var bestPath: List<Key> = emptyList()

    fun getAvailableKeysFrom(position: Position, keys: List<Key>): List<KeyDistance> {
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

    fun getInitialPosition() = map.entries.find { it.value is Entrance }!!.key

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

    private fun getAllKeys() = map.values.filterIsInstance<Key>()

    fun getBestKeyPath(): Pair<List<Key>, Int> {
        bestPathLength = Int.MAX_VALUE
        explorePossibleKeyPaths(emptyList(), 0)
        return Pair(bestPath, bestPathLength)
    }

    private fun explorePossibleKeyPaths(keyPath: List<Key>, pathLength: Int) {
        if (pathLength >= bestPathLength) {
            println("cutting path of length $pathLength - $keyPath")
            return
        }

        if (keyPath.containsAll(getAllKeys())) {
            bestPathLength = pathLength
            bestPath = keyPath
            println("found key path of length $bestPathLength : $bestPath")
        }

        val position = if (keyPath.isEmpty())
            getInitialPosition()
        else
            getKeyPosition(keyPath.last())

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