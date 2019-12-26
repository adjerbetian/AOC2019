package day15

import intCode.IntCodeComputer

class Game(val computer: IntCodeComputer) {
    val map = HashMap<Position, Tile>()
    val distances = HashMap<Position, Int>()
    var steps = 0
    var currentPosition = Position(0, 0)
    var direction: Direction = NORTH

    init {
        distances[currentPosition] = 0
        map[currentPosition] = PATH
        computer.outputFunction = fun(statusCode: Long) {
            val tile = when (statusCode.toInt()) {
                0 -> WALL
                1 -> PATH
                2 -> OXYGEN
                else -> throw Error("invalid status code: $statusCode")
            }
            treatTile(tile)
        }
    }

    fun explorePathToOxygen() {
        explore()
        try {
            computer.run()
        } catch (err: OxygenFound) {
            return
        }
    }

    private fun treatTile(tile: Tile) {
        map[currentPosition.moveInDirection(direction)] = tile

        when (tile) {
            WALL -> direction = direction.turnRight()
            else -> stepForward()
        }

        if (tile is OXYGEN) throw OxygenFound()
        if (currentPosition == Position(0, 0) && direction == NORTH) throw BackToStart()

        explore()
    }

    private fun stepForward() {
        currentPosition = currentPosition.moveInDirection(direction)
        distances[currentPosition] = ++steps

        direction = direction.turnLeft()
    }

    private fun explore() {
        computer.addInput(direction.code)
    }

    private fun minX() = map.keys.map { p -> p.x }.min()!!
    private fun maxX() = map.keys.map { p -> p.x }.max()!!
    private fun minY() = map.keys.map { p -> p.y }.min()!!
    private fun maxY() = map.keys.map { p -> p.y }.max()!!

    fun printMap() {
        println(toString())
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

    private fun stringifyPosition(position: Position, path: List<Position> = emptyList()): String {
        val colorizer = Colorizer()
        return when {
            map[position] == null -> " "
            map[position] is WALL -> colorizer.colorize("▯", colorizer.BLACK)
            map[position] is OXYGEN -> colorizer.colorize("●", colorizer.BLUE)
            position == currentPosition -> colorizer.colorize(direction.toString(), colorizer.RED)
            position == Position(0, 0) -> colorizer.colorize("●", colorizer.YELLOW)
            path.contains(position) -> colorizer.colorize("●", colorizer.GREEN)
            map[position] is PATH -> "."
            else -> throw Error("not possible")
        }
    }

    fun getPathToOxygen(): List<Position> {
        val path = mutableListOf(Position(0, 0))
        var position = Position(0, 0)

        while (position != currentPosition) {
            position = position.getNeighbors().maxBy { distances[it] ?: -1 }!!
            path.add(position)
        }

        return path
    }

    class OxygenFound : Error()
    class BackToStart : Error()
}

