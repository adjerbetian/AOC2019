package day15

data class Position(val x: Int, val y: Int) {
    fun moveInDirection(direction: Direction): Position {
        return when (direction) {
            NORTH -> Position(x, y + 1)
            SOUTH -> Position(x, y - 1)
            WEST -> Position(x - 1, y)
            EAST -> Position(x + 1, y)
        }
    }

    operator fun minus(p: Position) = Position(x - p.x, y - p.y)
}