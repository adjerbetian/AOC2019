package day3

class PathIterator(val directions: List<Direction>) {
    private var position = Position(0, 0)
    private var directionIndex = -1
    private var currentDirection: Direction = Up(0)
    private var distanceRun = 0

    fun getPosition(): Position {
        return position
    }

    fun getNextPosition(): Position {
        if (distanceRun == currentDirection.distance)
            takeNextDirection()
        step()
        return position
    }

    private fun takeNextDirection() {
        directionIndex++
        currentDirection = directions[directionIndex]
        distanceRun = 0
    }

    private fun step() {
        distanceRun++
        position = position.step(currentDirection)
    }

    fun isFinished(): Boolean {
        return directionIndex >= directions.size - 1 && distanceRun == currentDirection.distance
    }
}
