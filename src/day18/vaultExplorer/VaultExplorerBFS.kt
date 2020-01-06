package day18.vaultExplorer

import day18.vault.KeyPath
import day18.vaultGraph.VaultGraph
import day18.vaultGraph.buildVaultGraph

class VaultExplorerBFS(private val graph: VaultGraph) : VaultExplorer {
    private val pathMemory = KeyPathMemory()

    constructor(textMap: String) : this(buildVaultGraph(textMap))

    override fun getBestKeyPath(): KeyPath {
        var keyPaths = listOf(KeyPath(emptyList(), 0))

        for (i in graph.keys.indices)
            keyPaths = keyPaths.flatMap { explorePossibleKeyPaths(it) }

        return keyPaths.minBy { it.length }!!
    }

    private fun explorePossibleKeyPaths(keyPath: KeyPath) =
        graph.getAvailableKeyDistancesFrom(keyPath.last, keyPath.keys)
            .map { keyPath + it }
            .filter {
                if (pathMemory.hasSmaller(it))
                    false
                else {
                    pathMemory.add(it)
                    true
                }
            }
}