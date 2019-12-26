package day15

sealed class Tile
object WALL : Tile() {
    override fun toString() = "▯"
}

object PATH : Tile() {
    override fun toString() = "."
}

object OXYGEN : Tile() {
    override fun toString() = "X"
}