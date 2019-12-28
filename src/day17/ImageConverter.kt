package day17

object ImageConverter {
    fun print(image: Image) {
        val width = image.getWidth()
        val height = image.getHeight()

        for (top in 0 until height) {
            for (left in 0 until width) {
                val p = Position(left, top)

                if (image.isIntersection(p))
                    print("O")
                else
                    print(image[p])
            }
            print("\n")
        }
    }

    fun parse(outputs: List<Long>): Image {
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

        return Image(map)
    }
}