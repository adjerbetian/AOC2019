package day17

class ImagePrinter(private val image: Image) {
    fun print() {
        val width = image.getWidth()
        val height = image.getHeight()

        for (top in 0 until height) {
            for (left in 0 until width) {
                print(Position(left, top))
            }
            print("\n")
        }
    }

    private fun print(position: Position) {
        if (image.isIntersection(position))
            print("O")
        else
            print(image[position])
    }
}