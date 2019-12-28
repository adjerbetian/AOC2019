package day17

data class Position(val left: Int, val top: Int) {
    fun getNeighbors(): List<Position> {
        return listOf(
            Position(left - 1, top),
            Position(left + 1, top),
            Position(left, top - 1),
            Position(left, top + 1)
        )
    }

    fun getAlignmentParameter() = left * top
}

class Camera(outputs: List<Long>) {
    val map: HashMap<Position, Char> = HashMap()

    init {
        var left = 0
        var top = 0
        for (output in outputs) {
            if (output == '\n'.toLong()) {
                left = 0
                top++
            } else {
                map[Position(left, top)] = output.toChar()
                left++
            }
        }
    }

    fun getIntersections(): List<Position> {
        return map.keys.filter { isIntersection(it) }
    }

    private fun isIntersection(position: Position): Boolean {
        if (this[position] != '#') return false
        return position.getNeighbors().count { this[it] == '#' } > 2
    }

    operator fun get(x: Int, y: Int) = get(Position(x, y))
    operator fun get(position: Position) = map[position] ?: '.'

    override fun toString(): String {
        val width = map.keys.map { it.left }.max()!! + 1
        val height = map.keys.map { it.top }.max()!! + 1

        var result = ""
        for (top in 0 until height) {
            for (left in 0 until width) {
                if (isIntersection(Position(left, top)))
                    result += "O"
                else
                    result += this[left, top]
            }
            result += "\n"
        }
        return result
    }
}
