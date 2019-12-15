package day8

class Layer(private val width: Int, private val height: Int, layerString: String) {
    private val pixels = Array(height) { Array(width) { 0 } }

    init {
        var i = 0
        for (x in 0 until width) {
            for (y in 0 until height) {
                this[x, y] = layerString[i].toString().toInt()
                i++
            }
        }
    }

    operator fun get(x: Int, y: Int): Int {
        return pixels[y][x]
    }
    private operator fun set(x: Int, y: Int, value: Int) {
        pixels[y][x] = value
    }

    fun getNumberOf(n: Int): Int {
        var result = 0
        for (x in 0 until width) {
            for (y in 0 until height) {
                if(this[x,y] == n) result++
            }
        }
        return result
    }
}
