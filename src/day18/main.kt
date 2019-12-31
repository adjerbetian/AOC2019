package day18

import day18.vaultExplorer.VaultExplorer
import java.io.File

fun main() {
    val textMap = readInput("src/day18/input.txt")
    runPart1(textMap)
    runPart2()
}

fun runPart1(textMap: String) {
    val vault = VaultExplorer(textMap)
    println(vault.getBestKeyPath())

    // == 5102
 }

fun runPart2() {
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
