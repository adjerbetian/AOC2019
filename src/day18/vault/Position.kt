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
}

