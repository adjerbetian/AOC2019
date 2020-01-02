package day18.vault

class Vault(textMap: String) {
    private val map = parseKeyMap(textMap)
    val keys = map.values.filterIsInstance<Key>().toSet()
    val doors = map.values.filterIsInstance<Door>().toSet()
    val elements = map.values.filterIsInstance<TunnelElement>().toSet()

    operator fun get(position: Position) = map[position]!!
    operator fun get(element: TunnelElement) = map.entries.find { it.value == element }!!.key

    fun getNeighbors(position: Position) = position.getNeighbors().filter { this[it] !is Wall }

    fun getNumberOfElements() = keys.size + doors.size + 1
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


