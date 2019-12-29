package day18

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

sealed class Direction {
    abstract fun left(): Direction
    abstract fun right(): Direction
}

object Up : Direction() {
    override fun left() = Left
    override fun right() = Right
}

object Right : Direction() {
    override fun left() = Up
    override fun right() = Down
}

object Left : Direction() {
    override fun left() = Down
    override fun right() = Up
}

object Down : Direction() {
    override fun left() = Right
    override fun right() = Left
}