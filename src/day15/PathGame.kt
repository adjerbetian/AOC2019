package day15

import intCode.IntCodeComputer

class PathGame(val computer: IntCodeComputer) {
    private val droid = RepairDroid()
    private val shipMap = ShipMap(droid)

    init {
        shipMap[droid.position] = PATH
        computer.outputFunction = fun(statusCode: Long) {
            val tile = when (statusCode.toInt()) {
                0 -> WALL
                1 -> PATH
                2 -> OXYGEN
                else -> throw Error("invalid status code: $statusCode")
            }
            treatTile(tile)
        }
    }

    fun explorePathToOxygen() {
        explore()
        try {
            computer.run()
        } catch (err: OxygenFound) {
        }
    }

    private fun treatTile(tile: Tile) {
        shipMap[droid.getNextPosition()] = tile

        when (tile) {
            WALL -> droid.turn()
            else -> droid.stepForward()
        }

        if (tile is OXYGEN) throw OxygenFound()
        if (droid.position == Position(0, 0) && droid.direction == NORTH) throw BackToStart()

        explore()
    }

    private fun explore() {
        computer.addInput(droid.direction.code)
    }

    override fun toString() = shipMap.toString()

    fun getPathToOxygen() = shipMap.getPathToOxygen()

    class OxygenFound : Error()
    class BackToStart : Error()
}

