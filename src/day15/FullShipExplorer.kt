package day15

import intCode.IntCodeComputer

class FullShipExplorer(val computer: IntCodeComputer) {
    private val explorer = ShipExplorer(computer) { hasFoundOxygen() }

    private fun hasFoundOxygen() {
        if (explorer.droid.position == Position(0, 0) && explorer.droid.direction == NORTH)
            throw BackToBase()
    }

    fun exploreMap(): ShipMap {
        try {
            explorer.explore()
        } catch (e: BackToBase) {
        }
        return explorer.shipMap
    }

    override fun toString(): String = explorer.toString()

    class BackToBase : Error()
}

