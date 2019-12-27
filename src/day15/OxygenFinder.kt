package day15

import intCode.IntCodeComputer

class OxygenFinder(val computer: IntCodeComputer) {
    private val controller = DroidController(computer) { hasFoundOxygen(it) }

    private fun hasFoundOxygen(tile: Tile) {
        if (tile is OXYGEN) throw OxygenFound()
    }

    fun findOxygen() {
        try {
            controller.explore()
        } catch (e: OxygenFound) {
        }
    }

    fun getPathToOxygen() = controller.shipMap.getPathToOxygen()

    override fun toString(): String = controller.toString()

    class OxygenFound : Error()
}

