package day18

import java.io.File

fun main() {
    val textMap = readInput("src/day18/input.txt")
    runPart1(textMap)
    runPart2()
}

fun runPart1(textMap: String) {
    val vault = Vault(textMap)
    println(vault.getBestKeyPath())
    // < 5206
    // > 5000

    // != 5100
    // != 5200
    // != 5201
    // != 5205
    // != 5206
}

fun runPart2() {
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
