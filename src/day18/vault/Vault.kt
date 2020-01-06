package day18.vault

class Vault(textMap: String) {
    private val map = parseVaultMap(textMap)
    val keys = map.values.filterIsInstance<Key>().toSet()
    val doors = map.values.filterIsInstance<Door>().toSet()
    val elements = map.values.filterIsInstance<TunnelElement>().toSet()

    operator fun get(position: Position) = map.getValue(position)
    operator fun get(element: TunnelElement) = map.entries.find { it.value == element }!!.key

    fun getNeighbors(position: Position) = position.getNeighbors().filter { this[it] !is Wall }
    fun getNumberOfElements() = keys.size + doors.size + 1
}


