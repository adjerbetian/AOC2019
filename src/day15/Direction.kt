package day15

sealed class Direction(val code: Long) {
    abstract fun turnRight(): Direction
    abstract fun turnLeft(): Direction
}

object NORTH : Direction(1) {
    override fun turnRight() = EAST
    override fun turnLeft() = WEST
    override fun toString() = "△"
}

object SOUTH : Direction(2) {
    override fun turnRight() = WEST
    override fun turnLeft() = EAST
    override fun toString() = "▽"
}

object WEST : Direction(3) {
    override fun turnRight() = NORTH
    override fun turnLeft() = SOUTH
    override fun toString() = "◁"
}

object EAST : Direction(4) {
    override fun turnRight() = SOUTH
    override fun turnLeft() = NORTH
    override fun toString() = "▷"
}
