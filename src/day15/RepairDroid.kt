package day15

class RepairDroid {
    var position = Position(0, 0)
    var direction: Direction = NORTH
    var steps = 0

    fun stepForward() {
        position = getNextPosition()
        turnLeft()
        steps++
    }

    fun getNextPosition(): Position = position.moveInDirection(direction)

    private fun turnLeft() {
        direction = direction.turnLeft()
    }

    fun turn() = turnRight()

    private fun turnRight() {
        direction = direction.turnRight()
    }
}