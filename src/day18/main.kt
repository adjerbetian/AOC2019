package day18

import day18.vault.Vault
import day18.vaultExplorer.VaultExplorer
import day18.vaultGraph.VaultGraph1
import day18.vaultGraph.VaultGraph2
import java.io.File

fun main() {
    val textMap = readInput("src/day18/input.txt")
    runPart1(textMap)
    runPart2()
}

fun runPart1(textMap: String) {
    val vault = Vault(textMap)
    val graph = VaultGraph2(vault)
    val explorer = VaultExplorer(graph)
    println(explorer.getBestKeyPath())

    // == 5102
 }

fun runPart2() {
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
