package day15

import intCode.IntCodeComputer

typealias StatusCode = Int

class Game(val computer: IntCodeComputer) {
    val map = HashMap<Position, Tile>()
    val distances = HashMap<Position, Int>()
    var steps = 0
    var currentPosition = Position(0, 0)
    var direction: Direction = NORTH

    init {
        distances[currentPosition] = 0
        map[currentPosition] = PATH
        computer.outputFunction = { treatMoveResponse(it.toInt()) }
        requireMove()
    }

    private fun treatMoveResponse(statusCode: StatusCode) {
        val tile = when (statusCode) {
            0 -> WALL
            1 -> PATH
            2 -> OXYGEN
            else -> throw Error("invalid status code: $statusCode")
        }

        map[currentPosition.moveInDirection(direction)] = tile

//        val statusCode = if (currentPosition.moveInDirection(direction) == Position(0, 0)) {
//            FINISH
//        } else if (code == FINISH) {
//            STEP
//        } else {
//            code
//        }

        when (tile) {
            WALL -> direction = direction.turnRight()
            PATH -> stepForward()
            OXYGEN -> {
                showGame()
                println("path length: ${getPath().size}")
                throw Error("Oxygen found !")
            }
        }
        requireMove()
    }

    private fun stepForward() {
        currentPosition = currentPosition.moveInDirection(direction)
        distances[currentPosition] = ++steps

        direction = direction.turnLeft()
    }

    private fun requireMove() {
        computer.addInput(direction.code)
    }

    private fun minX() = map.keys.map { p -> p.x }.min()!!
    private fun maxX() = map.keys.map { p -> p.x }.max()!!
    private fun minY() = map.keys.map { p -> p.y }.min()!!
    private fun maxY() = map.keys.map { p -> p.y }.max()!!

    private fun showGame() {
        println(toString())
    }

    override fun toString(): String {
        val path = getPath()
        var result = ""
        val colorizer = Colorizer()
        for (y in maxY() downTo minY()) {
            for (x in minX()..maxX()) {
                if (Position(x, y) == currentPosition) {
                    result += colorizer.colorize(direction.toString(), colorizer.RED)
                } else if (Position(x, y) == Position(0, 0)) {
                    result += colorizer.colorize("●", colorizer.GREEN)
                } else if (path.contains(Position(x, y))) {
                    result += colorizer.colorize("●", colorizer.BLUE)
                } else {
                    result += colorizer.colorize(map[Position(x, y)]?.toString() ?: " ", colorizer.WHITE)
                }
            }
            result += "\n"
        }
        return result
    }

    private fun getPath(): List<Position> {
        val path = mutableListOf(Position(0, 0))
        var position = Position(0, 0)

        while (position != currentPosition) {
            position = listOf(
                position.moveInDirection(NORTH),
                position.moveInDirection(EAST),
                position.moveInDirection(SOUTH),
                position.moveInDirection(WEST)
            ).maxBy { distances[it] ?: -1 }!!
            path.add(position)
        }

        return path
    }
}

