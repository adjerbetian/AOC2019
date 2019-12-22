package day11

import intCode.IntCodeComputer

data class Position(val x: Int, val y: Int) {
    operator fun plus(v: Vector) = Position(x + v.x, y + v.y)
}

data class Vector(val x: Int, val y: Int)

val Up = Vector(0, 1)
val Right = Vector(1, 0)
val Left = Vector(-1, 0)
val Down = Vector(0, -1)

data class Panel(val position: Position, val color: Int = 0)

class PaintingRobot(val startColor: Int = 0) {
    var currentPosition = Position(0, 0)
    var currentDirection = Up
    val paintedPanels = mutableListOf<Panel>()

    fun moveForward() {
        currentPosition += currentDirection
    }

    fun turnRight() {
        currentDirection = when (currentDirection) {
            Up -> Right
            Right -> Down
            Down -> Left
            Left -> Up
            else -> throw Error("unknown direction $currentDirection")
        }
    }

    fun turnLeft() {
        currentDirection = when (currentDirection) {
            Up -> Left
            Left -> Down
            Down -> Right
            Right -> Up
            else -> throw Error("unknown direction $currentDirection")
        }
    }

    private fun paint(color: Long) {
        paint(color.toInt())
    }

    fun paint(color: Int) {
        if (color != 0 && color != 1) throw Error("unknown color $color")

        paintedPanels.removeIf { it.position == currentPosition }
        paintedPanels.add(Panel(currentPosition, color))
    }

    fun getCurrentColor(): Int {
        return getColor(currentPosition)
    }

    private fun getColor(position: Position): Int {
        val panel = paintedPanels.find { it.position == position }
        return panel?.color ?: 0
    }

    fun linkToComputer(computer: IntCodeComputer) {
        var hasPainted = false

        computer.outputFunction = { x ->
            if (!hasPainted)
                paint(x)
            else {
                when (x) {
                    0L -> turnLeft()
                    1L -> turnRight()
                    else -> throw Error("unknown x for turn $x")
                }
                moveForward()
                computer.addInput(getCurrentColor().toLong())
            }
            hasPainted = !hasPainted
        }

        computer.addInput(startColor.toLong())
    }

    override fun toString(): String {
        val minX = paintedPanels.map { it.position.x }.min()!!
        val maxX = paintedPanels.map { it.position.x }.max()!!
        val minY = paintedPanels.map { it.position.y }.min()!!
        val maxY = paintedPanels.map { it.position.y }.max()!!

        var result = ""
        for (y in maxY downTo minY) {
            for (x in minX..maxX)
                result += if (getColor(Position(x, y)) == 0) "   " else " X "
            result += "\n"
        }

        return result
    }
}