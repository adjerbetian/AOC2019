package day17

import intCode.IntCodeComputer

class Camera(val computer: IntCodeComputer) {

    fun getImage(): Image {
        computer.run()
        return Image(parseOutput(computer.outputs))
    }

    private fun parseOutput(outputs: List<Long>): HashMap<Position, Char> {
        val map = ImageMap()

        var left = 0
        var top = 0
        for (output in outputs) {
            if (output == '\n'.toLong()) {
                left = 0
                top++
            } else {
                map[Position(left, top)] = output.toChar()
                left++
            }
        }

        return map
    }
}

