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
        return bruteForceSolution()
    }

    private fun bruteForceSolution(): Long {
        var steps = 0L
        step()
        steps++

        while (true) {
            if (
                moons.map { it.position } == initialPositions &&
                moons.all { it.velocity == Velocity(0, 0, 0) }
            )
                return steps

            step()
            steps++
            if (steps % 10000000 == 0L)
                println("$steps - ${steps.toFloat() / 4686774924 * 100}% of 4686774924")
        }
    }
}