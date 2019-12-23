package day12

import kotlin.math.abs

data class Position(val x: Int, val y: Int, val z: Int) {
    override fun toString() = "pos=<x=$x, y=$y, z=$z>"

    operator fun plus(v: Velocity) =
        Position(x + v.x, y + v.y, z + v.z)

    fun norm() = abs(x) + abs(y) + abs(z)
}

data class Velocity(var x: Int, var y: Int, var z: Int) {
    override fun toString() = "vel=<x=$x, y=$y, z=$z>"

    fun norm() = abs(x) + abs(y) + abs(z)
}
