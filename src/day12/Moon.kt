package day12

class Moon(var position: Position) {
    var velocity = Velocity(0, 0, 0)

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

    override fun equals(other: Any?): Boolean {
        if (other !is Moon) return false
        return position == other.position && velocity == other.velocity
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + velocity.hashCode()
        return result
    }
}