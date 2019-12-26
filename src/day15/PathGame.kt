package day15

import intCode.IntCodeComputer

class PathGame(val computer: IntCodeComputer) {
    val map = HashMap<Position, Tile>()
    private val distances = HashMap<Position, Int>()
    private val robot = RepairDroid()

    init {
        distances[robot.position] = 0
        map[robot.position] = PATH
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
        map[robot.getNextPosition()] = tile

        when (tile) {
            WALL -> robot.turn()
            else -> {
                robot.stepForward()
                distances[robot.position] = robot.steps
            }
        }

        if (tile is OXYGEN) throw OxygenFound()
        if (robot.position == Position(0, 0) && robot.direction == NORTH) throw BackToStart()

        explore()
    }

    private fun explore() {
        computer.addInput(robot.direction.code)
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
            position == robot.position -> colorizer.colorize(robot.direction.toString(), colorizer.RED)
            position == Position(0, 0) -> colorizer.colorize("●", colorizer.YELLOW)
            path.contains(position) -> colorizer.colorize("●", colorizer.GREEN)
            map[position] is PATH -> "."
            else -> throw Error("not possible")
        }
    }

    fun getPathToOxygen(): List<Position> {
        val path = mutableListOf(Position(0, 0))
        var position = Position(0, 0)

        while (position != robot.position) {
            position = position.getNeighbors().maxBy { distances[it] ?: -1 }!!
            path.add(position)
        }

        return path
    }

    class OxygenFound : Error()
    class BackToStart : Error()
}

