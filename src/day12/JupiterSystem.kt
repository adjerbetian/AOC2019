package day12

import kotlin.math.abs

data class Position(val x: Int, val y: Int, val z: Int) {
    override fun toString() = "pos=<x=$x, y=$y, z=$z>"

    operator fun plus(v: Velocity) = Position(x + v.x, y + v.y, z + v.z)

    fun norm() = abs(x) + abs(y) + abs(z)
}

data class Velocity(var x: Int, var y: Int, var z: Int) {
    override fun toString() = "vel=<x=$x, y=$y, z=$z>"

    fun norm() = abs(x) + abs(y) + abs(z)
}

class Moon(var position: Position) {
    private var velocity = Velocity(0, 0, 0)

    override fun toString(): String {
        return "$position, $velocity"
    }

    fun applyGravityOf(m: Moon) {
        if (m.position.x > position.x) velocity.x++
        if (m.position.x < position.x) velocity.x--

        if (m.position.y > position.y) velocity.y++
        if (m.position.y < position.y) velocity.y--

        if (m.position.z > position.z) velocity.z++
        if (m.position.z < position.z) velocity.z--
    }

    fun applyVelocity() {
        position += velocity
    }

    fun getTotalEnergy() = getPotentialEnergy() * getKineticEnergy()

    private fun getPotentialEnergy() = position.norm()

    private fun getKineticEnergy() = velocity.norm()
}


class JupiterSystem(private val moons: List<Moon>) {
    override fun toString(): String {
        return moons.joinToString("\n")
    }

    fun step() {
        applyGravity()
        applyVelocities()
    }

    private fun applyGravity() {
        for (m1 in moons)
            for (m2 in moons)
                m1.applyGravityOf(m2)
    }

    private fun applyVelocities() {
        for (m in moons)
            m.applyVelocity()
    }

    fun getTotalEnergy() = moons.sumBy { it.getTotalEnergy() }
}