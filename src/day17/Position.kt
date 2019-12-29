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

    fun moveInDirection(direction: Direction): Position {
        return when (direction) {
            Up -> Position(left, top - 1)
            Right -> Position(left + 1, top)
            Left -> Position(left - 1, top)
            Down -> Position(left, top + 1)
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