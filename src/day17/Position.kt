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