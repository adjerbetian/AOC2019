package day12

class JupiterSystem(private val initialPositions: List<Position>) {
    private val moons = initialPositions.map { Moon(it) }

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

    fun getNumberOfStepsToReachAPreviousPosition(): Long {
        var periodX: Int? = null
        var periodY: Int? = null
        var periodZ: Int? = null

        var steps = 0
        step()
        steps++

        while (periodX == null || periodY == null || periodZ == null) {
            if (
                periodX == null &&
                moons.map { it.position.x } == initialPositions.map { it.x } &&
                moons.all { it.velocity.x == 0 }
            )
                periodX = steps

            if (
                periodY == null &&
                moons.map { it.position.y } == initialPositions.map { it.y } &&
                moons.all { it.velocity.y == 0 }
            )
                periodY = steps

            if (
                periodZ == null &&
                moons.map { it.position.z } == initialPositions.map { it.z } &&
                moons.all { it.velocity.z == 0 }
            )
                periodZ = steps

            step()
            steps++
        }
        return lcm(lcm(periodX, periodY), periodZ)
    }
}

fun lcm(a: Int, b: Int) = lcm(a.toLong(), b.toLong())
fun lcm(a: Long, b: Int) = lcm(a, b.toLong())
fun lcm(a: Int, b: Long) = lcm(a.toLong(), b)

fun lcm(a: Long, b: Long): Long {
    if (a < b) return lcm(b, a)

    var result = a
    while (result % b != 0L)
        result += a
    return result
}