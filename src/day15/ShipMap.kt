package day15

class ShipMap(private val droid: RepairDroid) {
    val tileMap = HashMap<Position, Tile>()
    private val distanceMap = HashMap<Position, Int>()

    operator fun get(position: Position): Tile = tileMap[position]!!

    operator fun set(position: Position, tile: Tile) {
        tileMap[position] = tile

        if (tile !is WALL)
            distanceMap[position] = droid.steps
    }

    fun getPathToOxygen(): List<Position> {
        var position = Position(0, 0)
        val path = mutableListOf(position)

        while (position != droid.position) {
            position = position.getNeighbors().maxBy { distanceMap[it] ?: -1 }!!
            path.add(position)
        }

        return path
    }

    override fun toString(): String {
        val path = getPathToOxygen()
        var result = ""
        for (y in maxY() downTo minY()) {
            for (x in minX()..maxX()) {
                result += stringifyPosition(Position(x, y), path)
            }
            result += "\n"
        }
        return result
    }

    private fun stringifyPosition(position: Position, path: List<Position>): String {
        val colorizer = Colorizer()
        return when {
            tileMap[position] == null -> " "
            tileMap[position] is WALL -> colorizer.colorize("▯", colorizer.BLACK)
            tileMap[position] is OXYGEN -> colorizer.colorize("●", colorizer.BLUE)
            position == droid.position -> colorizer.colorize(droid.direction.toString(), colorizer.RED)
            position == Position(0, 0) -> colorizer.colorize("●", colorizer.YELLOW)
            path.contains(position) -> colorizer.colorize("●", colorizer.GREEN)
            tileMap[position] is PATH -> "."
            else -> throw Error("not possible")
        }
    }

    private fun minX() = tileMap.keys.map { p -> p.x }.min()!!
    private fun maxX() = tileMap.keys.map { p -> p.x }.max()!!
    private fun minY() = tileMap.keys.map { p -> p.y }.min()!!
    private fun maxY() = tileMap.keys.map { p -> p.y }.max()!!
}