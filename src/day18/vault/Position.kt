package day18.vault

data class Position(val x: Int, val y: Int) {
    fun getNeighbors(): List<Position> {
        return listOf(
            Position(x - 1, y),
            Position(x + 1, y),
            Position(x, y - 1),
            Position(x, y + 1)
        )
    }

    fun moveInDirection(direction: Direction): Position {
        return when (direction) {
            Up -> Position(x, y - 1)
            Right -> Position(x + 1, y)
            Left -> Position(x - 1, y)
            Down -> Position(x, y + 1)
        }
    }
}

