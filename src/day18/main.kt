package day18

import day18.vault.Vault
import day18.vaultExplorer.VaultExplorerBFS
import day18.vaultExplorer.VaultExplorerDFS
import day18.vaultGraph.VaultGraphImpl
import java.io.File
import java.time.Duration

fun main() {
//    runPart1_BFS() // == 5102
    runPart1_DFS() // == 5102
    runPart2()
}

fun runPart1_BFS() {
    val textMap = readInput("src/day18/input-part1.txt")
    val vault = Vault(textMap)
    val graph = VaultGraphImpl(vault)
    val explorer = VaultExplorerBFS(graph, 100000)
    println(explorer.getBestKeyPath())
}

fun runPart1_DFS() {
    val textMap = readInput("src/day18/input-part1.txt")
    val vault = Vault(textMap)
    val graph = VaultGraphImpl(vault)
    val explorer = VaultExplorerDFS(graph, Duration.ofSeconds(200))
    println(explorer.getBestKeyPath())
}

fun runPart2() {
}

fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
