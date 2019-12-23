package day12

fun main() {
    val system = JupiterSystem(
        listOf(
            Position(-1, 7, 3),
            Position(12, 2, -13),
            Position(14, 18, -8),
            Position(17, 4, -4)
        )
    )

    repeat(1000) { system.step() }

    println("After 1000 steps the total energy is ${system.getTotalEnergy()}")
}
