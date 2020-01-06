package day18

import day18.vault.Vault
import day18.vault.splitVaultMaps
import day18.vaultExplorer.VaultExplorerBFS
import day18.vaultExplorer.VaultExplorerDFS
import day18.vaultGraph.VaultGraphImpl
import java.io.File

fun main() {
    runPart1_DFS()
    runPart1_BFS()
//    runPart2()
}

private fun runPart1_DFS() {
    val explorer = VaultExplorerDFS(buildGraph())
    val (path, length) = explorer.getBestKeyPath()
    println("DFS - The best length is $length for the path $path")
}

private fun runPart1_BFS() {
    val explorer = VaultExplorerBFS(buildGraph())
    val (path, length) = explorer.getBestKeyPath()
    println("BFS - The best length is $length for the path $path")
}

private fun buildGraph() = VaultGraphImpl(Vault(readInput("src/day18/input-part1.txt")))

private fun runPart2() {
    val textMap = readInput("src/day18/input-part2.txt")
    val textMaps = splitVaultMaps(textMap)

    textMaps.forEach { println(it); println() }

    val vaults = textMaps.map { Vault(it) }
    val graphs = vaults.map { VaultGraphImpl(it) }
}

private fun readInput(stringPath: String): String {
    return File(stringPath).readText(Charsets.UTF_8)
}
