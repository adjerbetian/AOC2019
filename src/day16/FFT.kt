package day16

import kotlin.math.abs

class FFT(val input: String) {
    private var current = input.map { it.toString().toInt() }.toMutableList()

    fun apply(): String {
        current = current.indices.map { abs(apply(it)) % 10 }.toMutableList()
        return getCurrent()
    }

    private fun apply(l: Int): Int {
        return current.mapIndexed { k, ak -> p(k, l) * ak }.sum()
    }

    private fun p(i: Int, j: Int): Int {
        val index = ((i + 1) / (j + 1)) % 4
        return arrayOf(0, 1, 0, -1)[index]
    }

    fun getCurrent(): String {
        return current.joinToString("")
    }

    fun getCurrentWithOffset(): String {
        val offset = getOffset()
        return getCurrent().substring(offset, offset + 8)
    }

    fun apply2() {
        for (i in current.lastIndex - 1 downTo getOffset()) {
            current[i] = abs(current[i] + current[i + 1]) % 10
        }
    }

    private fun getOffset(): Int {
        return input.substring(0, 7).toInt()
    }
}