package day15

import intCode.IntCodeComputer
import java.lang.Thread.sleep

typealias Direction = Int
typealias StatusCode = Int

const val NORTH = 1
const val SOUTH = 2
const val WEST = 3
const val EAST = 4

const val WALL = 0
const val STEP = 1
const val FINISH = 2

data class Position(val x: Int, val y: Int) {
    fun moveInDirection(direction: Direction): Position {
        return when (direction) {
            NORTH -> Position(x, y + 1)
            SOUTH -> Position(x, y - 1)
            WEST -> Position(x - 1, y)
            EAST -> Position(x + 1, y)
            else -> throw Error("invalid direction $direction")
        }
    }

    operator fun minus(p: Position) = Position(x - p.x, y - p.y)
}

class Game(val computer: IntCodeComputer) {
    val map = HashMap<Position, StatusCode>()
    val distances = HashMap<Position, Int>()
    var steps = 0
    var currentPosition = Position(0, 0)
    var direction = 1

    init {
        distances[currentPosition] = 0
        map[currentPosition] = STEP
        computer.outputFunction = { treatMoveResponse(it.toInt()) }
        requireMove()
    }

    private fun treatMoveResponse(statusCode: StatusCode) {
        map[currentPosition.moveInDirection(direction)] = statusCode

        when (statusCode) {
            WALL -> turnRight()
            STEP -> stepForward()
            FINISH -> {
                showGame()
                println("path length: ${getPath().size}")
                throw Error("FINISHED")
            }
            else -> throw Error("unknown status code $statusCode")
        }
        requireMove()
    }

    private fun turnRight() {
        direction = when (direction) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
            else -> throw Error("not possible")
        }
    }

    private fun turnLeft() {
        direction = when (direction) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
            else -> throw Error("not possible")
        }
    }

    private fun stepForward() {
        currentPosition = currentPosition.moveInDirection(direction)
        distances[currentPosition] = ++steps

        turnLeft()
    }

    private fun requireMove() {
        computer.addInput(direction.toLong())
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
                    result += colorizer.colorize(
                        when (direction) {
                            NORTH -> "△"
                            EAST -> "▷"
                            SOUTH -> "▽"
                            WEST -> "◁"
                            else -> throw Error("not possible")
                        }, colorizer.RED
                    )
                } else if (Position(x, y) == Position(0, 0)) {
                    result += colorizer.colorize("●", colorizer.GREEN)
                } else if (path.contains(Position(x, y))) {
                    result += colorizer.colorize("●", colorizer.BLUE)
                } else {
                    result += colorizer.colorize(
                        when (map[Position(x, y)]) {
                            null -> " "
                            WALL -> "▯"
                            STEP -> "."
                            FINISH -> "X"
                            else -> throw Error("not possible")
                        }, colorizer.WHITE
                    )
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

class Colorizer() {
    val RESET = "\u001B[0m"
    val BLACK = "\u001B[30m"
    val RED = "\u001B[31m"
    val GREEN = "\u001B[32m"
    val YELLOW = "\u001B[33m"
    val BLUE = "\u001B[34m"
    val PURPLE = "\u001B[35m"
    val CYAN = "\u001B[36m"
    val WHITE = "\u001B[37m"

    fun colorize(message: String, color: String): String {
        return color + message + RESET
    }
}
