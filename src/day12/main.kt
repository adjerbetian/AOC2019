package day12

fun main() {
    val positions = listOf(
        Position(-1, 7, 3),
        Position(12, 2, -13),
        Position(14, 18, -8),
        Position(17, 4, -4)
    )

    part1(positions)
    part2(positions)
}

fun part1(positions: List<Position>) {
    val system = JupiterSystem(positions)

    repeat(1000) { system.step() }

    println("After 1000 steps the total energy is ${system.getTotalEnergy()}")
}

fun part2(positions: List<Position>) {
    val system = JupiterSystem(positions)

    println("Period found for ${system.getNumberOfStepsToReachAPreviousPosition()}")
}
