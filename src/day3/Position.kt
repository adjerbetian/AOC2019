package day3

data class Position(val x: Int, val y: Int) {
    fun move(direction: Direction): Position {
        return moveOf(direction, direction.distance)
    }

    fun step(direction: Direction): Position {
        return moveOf(direction, 1)
    }

    private fun moveOf(direction: Direction, d: Int): Position {
        return when (direction) {
            is Up -> Position(x, y + d)
            is Down -> Position(x, y - d)
            is Right -> Position(x + d, y)
            is Left -> Position(x - d, y)
        }
    }

    fun getManhattanDistance(): Int {
        return x + y
    }
}
