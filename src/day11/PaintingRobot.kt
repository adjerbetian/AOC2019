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

class PaintingRobot {
    var position = Position(0, 0)
    var direction = Up
    val paintedPanels = mutableListOf<Panel>()

    fun moveForward() {
        position += direction
    }

    fun turnRight() {
        direction = when (direction) {
            Up -> Right
            Right -> Down
            Down -> Left
            Left -> Up
            else -> throw Error("unknown direction $direction")
        }
    }

    fun turnLeft() {
        direction = when (direction) {
            Up -> Left
            Left -> Down
            Down -> Right
            Right -> Up
            else -> throw Error("unknown direction $direction")
        }
    }

    private fun paint(color: Long) {
        paint(color.toInt())
    }

    fun paint(color: Int) {
        if (color != 0 && color != 1) throw Error("unknown color $color")

        paintedPanels.removeIf { it.position == position }
        paintedPanels.add(Panel(position, color))
    }

    fun getCurrentColor(): Int {
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

        computer.addInput(getCurrentColor().toLong())
    }
}