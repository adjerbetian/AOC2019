package day16

import kotlin.math.abs

class FFT(input: String) {
    private val pattern = arrayOf(0, 1, 0, -1)
    private var current = input.map { it.toString().toInt() }

    fun apply(): String {
        val output = MutableList(current.size) { 0 }
        for (i in 0 until current.size) {
            output[i] = apply(i)
            output[i] = abs(output[i]) % 10
        }
        current = output
        return getCurrent()
    }

    private fun apply(index: Int): Int {
        var result = 0
        for (i in 0 until current.size) {
            val patternIndex = ((i + 1) / (index + 1)) % pattern.size
            result += current[i] * pattern[patternIndex]
        }
        return result
    }

    fun getCurrent(): String {
        return current.joinToString("")
    }
}