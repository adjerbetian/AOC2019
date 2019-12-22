package day10

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

data class Vector(val x: Int, val y: Int) {
    fun isAlignedWith(v: Vector) = vectorial(v) == 0 && dot(v) >= 0
    fun vectorial(v: Vector) = x * v.y - y * v.x
    fun dot(v: Vector) = x * v.x + y * v.y
    fun norm1() = abs(x) + abs(y)
    fun angleWith(v: Vector): Float {
        val angle = v.angle() - angle()
        return if(angle < 0)
            angle + 2 * PI.toFloat()
        else
            angle
    }
    private fun angle() = atan2(y.toFloat(), x.toFloat())
}
