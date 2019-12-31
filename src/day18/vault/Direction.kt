package day18.vault

sealed class Direction {
    abstract fun left(): Direction
    abstract fun right(): Direction
}

object Up : Direction() {
    override fun left() = Left
    override fun right() = Right
}

object Right : Direction() {
    override fun left() = Up
    override fun right() = Down
}

object Left : Direction() {
    override fun left() = Down
    override fun right() = Up
}

object Down : Direction() {
    override fun left() = Right
    override fun right() = Left
}