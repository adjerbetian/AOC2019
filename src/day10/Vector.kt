package day10

import kotlin.math.abs

data class Vector(val x: Int, val y: Int) {
    fun vectorial(v: Vector) =  x * v.y - y * v.x

    fun dot(v: Vector) = x * v.x + y * v.y
    fun norm1() = abs(x) + abs(y)
}
