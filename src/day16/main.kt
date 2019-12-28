package day16

import java.io.File

fun main() {
    val input = readInput("src/day16/input.txt")
    runPart1(input)
    runPart2(input)
}

fun runPart1(input: String) {
    val fft = FFT(input)

    repeat(100) { fft.apply() }

    println(fft.getCurrent().substring(0, 8))
}

fun runPart2(input: String) {
    val fft = FFT(input.repeat(10000))

    repeat(100) { fft.apply() }

    println(fft.getCurrentWithOffset())
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
