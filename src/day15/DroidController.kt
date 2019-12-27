package day15

import intCode.IntCodeComputer

class DroidController(val computer: IntCodeComputer, val onTile: (tile: Tile) -> Unit) {
    val droid = RepairDroid()
    val shipMap = ShipMap(droid)

    init {
        shipMap[droid.position] = PATH
        computer.outputFunction = fun(statusCode: Long) {
            val tile = when (statusCode.toInt()) {
                0 -> WALL
                1 -> PATH
                2 -> OXYGEN
                else -> throw Error("invalid status code: $statusCode")
            }
            discoverTile(tile)
        }
    }

    fun explore() {
        continueExploration()
        computer.run()
    }

    private fun discoverTile(tile: Tile) {
        shipMap[droid.getNextPosition()] = tile

        when (tile) {
            WALL -> droid.turn()
            else -> droid.stepForward()
        }

        onTile(tile)

        continueExploration()
    }

    private fun continueExploration() {
        computer.addInput(droid.direction.code)
    }

    override fun toString() = shipMap.toString()
}

