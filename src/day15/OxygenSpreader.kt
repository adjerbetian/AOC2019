package day15

import intCode.IntCodeComputer

class OxygenSpreader(val computer: IntCodeComputer) {
    private val controller = DroidController(computer) { hasFoundOxygen() }

    private fun hasFoundOxygen() {
        if (controller.droid.position == Position(0, 0) && controller.droid.direction == NORTH)
            throw BackToBase()
    }

    fun exploreMap() {
        try {
            controller.explore()
        } catch (e: BackToBase) {
        }
    }

    fun spreadOxygen(): Int {
        println(controller.toString())
        return 0
    }

    override fun toString(): String = controller.toString()

    class BackToBase : Error()
}

