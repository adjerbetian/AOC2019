package day17

typealias ImageMap = HashMap<Position, Char>

class Image(private val map: ImageMap) {
    fun getIntersections(): List<Position> {
        return map.keys.filter { isIntersection(it) }
    }

    fun isIntersection(position: Position): Boolean {
        if (this[position] != '#') return false
        return position.getNeighbors().count { this[it] == '#' } > 2
    }

    private fun isScaffold(position: Position): Boolean {
        return this[position] == '#'
    }

    fun getScaffoldPath(): List<Char> {
        val robot = getRobot()
        while (robot.getPossibleNext().any { isScaffold(it) }) {
            when {
                isScaffold(robot.getForward()) -> {
                    robot.moveForward()
                }
                isScaffold(robot.getLeft()) -> {
                    robot.turnLeft()
                    robot.moveForward()
                }
                isScaffold(robot.getRight()) -> {
                    robot.turnRight()
                    robot.moveForward()
                }
            }
        }
        return robot.getPath()
    }

    private fun getRobot(): VacuumRobot {
        val position = map.keys.find { isVacuumRobot(it) }!!
        val direction = when (this[position]) {
            '^' -> Up
            '>' -> Right
            'v' -> Down
            '<' -> Left
            else -> throw Error("not possible")
        }
        return VacuumRobot(position, direction)
    }

    private fun isVacuumRobot(position: Position): Boolean {
        return listOf('^', '>', 'v', '<').contains(this[position])
    }

    operator fun get(x: Int, y: Int) = get(Position(x, y))
    operator fun get(position: Position) = map[position] ?: '.'

    fun getWidth() = map.keys.map { it.left }.max()!! + 1
    fun getHeight() = map.keys.map { it.top }.max()!! + 1
}

