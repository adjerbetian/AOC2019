package day18.vaultExplorer

import day18.vault.KeyPath
import day18.vaultGraph.VaultGraph
import day18.vaultGraph.buildVaultGraph

class VaultExplorerBFS(private val graph: VaultGraph, private val size: Int) : VaultExplorer {
    constructor(textMap: String, size: Int) : this(buildVaultGraph(textMap), size)

    override fun getBestKeyPath(): KeyPath {
        var keyPaths = listOf(KeyPath(emptyList(), 0))

        for (i in graph.keys.indices) {
            keyPaths = keyPaths.flatMap { explorePossibleKeyPaths(it) }
                .sortedBy { it.length }
                .take(size)
            println("$i / ${graph.keys.size}")
        }
        return keyPaths.first()
    }

    private fun explorePossibleKeyPaths(keyPath: KeyPath) =
        graph.getAvailableKeyDistancesFrom(keyPath.last, keyPath.keys)
            .map { keyPath + it }
}