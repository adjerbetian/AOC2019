package day17

object ImageConverter {
    fun print(image: Image) = println(stringify(image))

    fun stringify(image: Image): String {
        return stringifyWith(image) { null }
    }

    fun stringifyWith(image: Image, positionStringifier: (Position) -> String?): String {
        val height = image.getHeight()
        val width = image.getWidth()

        var result = ""
        for (top in 0 until height) {
            for (left in 0 until width)
                result += positionStringifier(Position(left, top)) ?: image[Position(left, top)]
            result += "\n"
        }
        return result.trimEnd()
    }

    fun parse(outputs: List<Long>): Image = parse(
        outputs.joinToString("") { it.toChar().toString() }
    )

    fun parse(outputs: String): Image {
        val map = ImageMap()

        var left = 0
        var top = 0
        for (output in outputs) {
            if (output == '\n') {
                left = 0
                top++
            } else {
                map[Position(left, top)] = output
                left++
            }
        }

        return Image(map)
    }
}