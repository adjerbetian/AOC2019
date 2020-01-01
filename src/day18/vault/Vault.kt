package day18.vault

class Vault(textMap: String) {
    private val map = parseKeyMap(textMap)
    val keys = map.values.filterIsInstance<Key>()
    private val doors = map.values.filterIsInstance<Door>()
    private val keyPositions = keys.associateWith { key -> map.entries.find { it.value == key }!!.key }
    private val doorPositions = doors.associateWith { door -> map.entries.find { it.value == door }!!.key }

    operator fun get(position: Position) = map[position]!!
    operator fun get(key: Key) = keyPositions.getValue(key)
    operator fun get(door: Door) = doorPositions.getValue(door)
    operator fun get(entrance: Entrance) = map.entries.find { it.value == entrance }!!.key

    fun getNeighbors(position: Position) = position.getNeighbors().filter { this[it] !is Wall }
}

private fun parseKeyMap(textMap: String): HashMap<Position, MapElement> {
    val result = HashMap<Position, MapElement>()

    var x = 0
    var y = 0
    for (c in textMap) {
        if (c == '\n') {
            x = 0
            y++
        } else {
            result[Position(x++, y)] = when {
                c == '#' -> Wall
                c == '.' -> Tunnel()
                c == '@' -> Entrance
                c.isLetter() && c.isLowerCase() -> Key(c)
                c.isLetter() && c.isUpperCase() -> Door(c)
                else -> throw Error("character not recognized: $c")
            }
        }
    }

    return result
}


