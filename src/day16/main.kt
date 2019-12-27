package day16

import java.io.File

fun main() {
    val input = readInput("src/day16/input.txt")

    val fft = FFT(input)

    repeat(100) { fft.apply() }
    println(fft.getCurrent().substring(0, 8))
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
