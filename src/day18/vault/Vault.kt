package day18.vault

class Vault(textMap: String) {
    private val map = parseKeyMap(textMap)
    val keys = map.values.filterIsInstance<Key>().toSet()
    val doors = map.values.filterIsInstance<Door>().toSet()
    val elements = map.values.filterIsInstance<TunnelElement>().toSet()

    operator fun get(position: Position) = map.getValue(position)
    operator fun get(element: TunnelElement) = map.entries.find { it.value == element }!!.key

    fun getNeighbors(position: Position) = position.getNeighbors().filter { this[it] !is Wall }
    fun getNumberOfElements() = keys.size + doors.size + 1
}

private fun parseKeyMap(textMap: String): Map<Position, MapElement> {
    return textMap
        .split("\n")
        .mapIndexed { y, row ->
            row.mapIndexed { x, c -> Pair(Position(x, y), parseChar(c)) }
        }
        .flatten()
        .associate { it }
}

private fun parseChar(c: Char) = when {
    c == '#' -> Wall
    c == '.' -> Tunnel
    c == '@' -> Entrance
    c.isLetter() && c.isLowerCase() -> Key(c)
    c.isLetter() && c.isUpperCase() -> Door(c)
    else -> throw Error("character not recognized: $c")
}


