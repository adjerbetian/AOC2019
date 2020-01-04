package day18

import day18.vault.Vault
import day18.vaultExplorer.VaultExplorerBFS
import day18.vaultExplorer.VaultExplorerDFS
import day18.vaultGraph.VaultGraph1
import day18.vaultGraph.VaultGraph2
import java.io.File

fun main() {
    val textMap = readInput("src/day18/input.txt")
    runPart1_BFS(textMap) // == 5102
    runPart2()
}

fun runPart1_BFS(textMap: String) {
    val vault = Vault(textMap)
    val graph = VaultGraph2(vault)
    val explorer = VaultExplorerBFS(graph, 100000)
    println(explorer.getBestKeyPath())
}

fun runPart1_DFS(textMap: String) {
    val vault = Vault(textMap)
    val graph = VaultGraph1(vault)
    val explorer = VaultExplorerDFS(graph)
    println(explorer.getBestKeyPath())
}

fun runPart2() {
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
