package day17

typealias ImageMap = HashMap<Position, Char>

class Image(private val map: ImageMap) {
    fun getIntersections(): List<Position> {
        return map.keys.filter { isIntersection(it) }
    }

    fun isIntersection(position: Position): Boolean {
        if (this[position] != '#') return false
        return position.getNeighbors().count { this[it] == '#' } > 2
    }

    operator fun get(x: Int, y: Int) = get(Position(x, y))
    operator fun get(position: Position) = map[position] ?: '.'

    fun getWidth() = map.keys.map { it.left }.max()!! + 1
    fun getHeight() = map.keys.map { it.top }.max()!! + 1
}