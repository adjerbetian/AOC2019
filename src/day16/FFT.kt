package day16

import kotlin.math.abs

class FFT(val input: String) {
    private val pattern = arrayOf(0, 1, 0, -1)
    private var current = input.map { it.toString().toInt() }.toIntArray()

    fun apply(): String {
        val output = IntArray(current.size)
        for (i in current.indices) {
            output[i] = apply(i)
            output[i] = abs(output[i]) % 10
        }
        current = output
        return getCurrent()
    }

    private fun apply(index: Int): Int {
        var result = 0
        for (i in current.indices) {
            val patternIndex = ((i + 1) / (index + 1)) % pattern.size
            result += current[i] * pattern[patternIndex]
        }
        return result
    }

    fun getCurrent(): String {
        return current.joinToString("")
    }

    fun getCurrentWithOffset(): String {
        val offset = input.substring(0, 7).toInt()
        return getCurrent().substring(offset, offset + 8)
    }
}