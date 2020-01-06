package day18.vaultExplorer

import day18.vault.KeyPath
import day18.vaultGraph.VaultGraph
import day18.vaultGraph.buildVaultGraph

class VaultExplorerDFS(private val graph: VaultGraph) : VaultExplorer {
    private val pathMemory = KeyPathMemory()
    private var bestPath = KeyPath(emptyList(), Int.MAX_VALUE)

    constructor(textMap: String) : this(buildVaultGraph(textMap))

    override fun getBestKeyPath(): KeyPath {
        explorePossibleKeyPaths(KeyPath(emptyList(), 0))
        return bestPath
    }

    private fun explorePossibleKeyPaths(keyPath: KeyPath) {
        if (pathMemory.hasSmaller(keyPath)) return
        pathMemory.add(keyPath)

        if (keyPath.length + getMinRemainingDistance(keyPath) >= bestPath.length) return

        if (keyPath.size == graph.keys.size) {
            bestPath = keyPath
            return
        }

        getNextKeys(keyPath).forEach {
            explorePossibleKeyPaths(keyPath + it)
        }
    }

    private fun getMinRemainingDistance(keyPath: KeyPath) =
        graph.getMaxDistanceToKey(keyPath.last, keyPath.keys)

    private fun getNextKeys(keyPath: KeyPath) =
        graph.getAvailableKeyDistancesFrom(keyPath.last, keyPath.keys)
}
