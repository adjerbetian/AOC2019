package day15

import intCode.IntCodeComputer

class OxygenShipExplorer(val computer: IntCodeComputer) {
    private val explorer = ShipExplorer(computer) { hasFoundOxygen(it) }

    private fun hasFoundOxygen(tile: Tile) {
        if (tile is OXYGEN) throw OxygenFound()
    }

    fun findOxygen() {
        try {
            explorer.explore()
        } catch (e: OxygenFound) {
        }
    }

    fun getPathToOxygen() = explorer.shipMap.getPathToOxygen()

    override fun toString(): String = explorer.toString()

    class OxygenFound : Error()
}

