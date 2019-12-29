package day17

class VacuumRobot(var position: Position, var direction: Direction) {
    private val path = mutableListOf<Char>()

    fun moveForward() {
        position = getForward()
        path.add('F')
    }

    fun getForward() = position.moveInDirection(direction)
    fun getLeft() = position.moveInDirection(direction.left())
    fun getRight() = position.moveInDirection(direction.right())
    fun getPossibleNext() = listOf(getForward(), getLeft(), getRight())

    fun turnRight() {
        direction = direction.right()
        path.add('R')
    }

    fun turnLeft() {
        direction = direction.left()
        path.add('L')
    }

    fun getPath(): List<String> {
        val result = mutableListOf("0")
        for (i in path.indices) {
            if (path[i] == 'R') {
                result.add("R")
                result.add("0")
            }
            if (path[i] == 'L') {
                result.add("L")
                result.add("0")
            }
            if (path[i] == 'F') {
                result[result.lastIndex] = (result.last().toInt() + 1).toString()
            }
        }
        if (result[0] == "0")
            result.removeAt(0)

        return result
    }
}