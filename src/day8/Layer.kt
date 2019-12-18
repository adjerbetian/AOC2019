package day8

const val TRANSPARENT = 2

class Layer(private val width: Int, private val height: Int) {
    private val pixels = Array(height) { Array(width) { TRANSPARENT } }

    constructor(width: Int, height: Int, layerString: String) : this(width, height) {
        var i = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                this[x, y] = layerString[i].toString().toInt()
                i++
            }
        }
    }

    operator fun get(x: Int, y: Int): Int {
        return pixels[y][x]
    }

    operator fun set(x: Int, y: Int, value: Int) {
        pixels[y][x] = value
    }

    fun getNumberOf(n: Int): Int {
        var result = 0
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (this[x, y] == n) result++
            }
        }
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (!(other is Layer || other is String)) return false

        val otherLayer: Layer = if (other is String) Layer(width, height, other) else other as Layer

        for (x in 0 until width) {
            for (y in 0 until height) {
                if (this[x, y] != otherLayer[x, y]) return false
            }
        }
        return true
    }

    fun mergeDownWith(downLayer: Layer): Layer {
        val result = Layer(width, height)
        iteratePixels { x: Int, y: Int ->
            result[x, y] = if (this[x, y] != TRANSPARENT) this[x, y] else downLayer[x, y]
        }
        return result
    }

    fun getCode(): String {
        var result = ""
        iteratePixels { x: Int, y: Int ->
            result += this[x, y].toString()
        }
        return result
    }

    fun getImage(): String {
        var result = ""
        for (y in 0 until height) {
            for (x in 0 until width) {
                result += if (this[x, y] == 1) " X " else "   "
            }
            result += "\n"
        }
        return result
    }

    private fun iteratePixels(callback: (x: Int, y: Int) -> Unit) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                callback(x, y)
            }
        }
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + pixels.contentDeepHashCode()
        return result
    }
}
