package day18

data class KeyDistance(val key: Key, val distance: Int) {
    override fun toString() = "$key: $distance"
}


class Vault(textMap: String) {
    private val map = parseKeyMap(textMap)
    val keys = map.values.filterIsInstance<Key>()
    private val doors = map.values.filterIsInstance<Door>()
    private val keyPositions = keys.associateWith { key -> map.entries.find { it.value == key }!!.key }
    private val doorPositions = doors.associateWith { door -> map.entries.find { it.value == door }!!.key }

    operator fun get(position: Position) = map[position]!!
    operator fun get(key: Key) = keyPositions.getValue(key)
    operator fun get(door: Door) = doorPositions.getValue(door)

    fun getNeighbors(position: Position) = position.getNeighbors().filter { this[it] !is Wall }
}
