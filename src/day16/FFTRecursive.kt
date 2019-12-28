package day16

import kotlin.math.abs

class FFTRecursive(val input: String) {
    private val pattern = arrayOf(0, 1, 0, -1)
    private val initial = input.map { it.toString().toInt() }.toIntArray()
    private val computedValues = HashMap<Int, IntArray>()

    fun apply(iterations: Int): String {
        return initial.indices.map { getValueAt(it, iterations) }.joinToString("")
    }

    fun getValues(from: Int, to: Int, iterations: Int): String {
        return (from until to).map { getValueAt(it, iterations) }.joinToString("")
    }

    fun getValueAt(position: Int, iterations: Int): Int {
        if (iterations == 0) return initial[position]

        if (computedValues[iterations] == null)
            computedValues[iterations] = IntArray(input.length)

        if (computedValues[iterations]!![position] == 0)
            computedValues[iterations]!![position] = computeValueAt(position, iterations)

        return computedValues[iterations]!![position]
    }

    private fun computeValueAt(position: Int, iterations: Int): Int {
        var result = 0
        for (i in initial.indices) {
            val patternIndex = (i + 1) / (position + 1)
            val multiplicator = pattern[patternIndex % pattern.size]
            if (multiplicator == 0)
                continue
            else
                result += getValueAt(i, iterations - 1) * multiplicator
        }
        return abs(result) % 10
    }
}