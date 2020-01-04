package day18.vaultExplorer

import day18.vault.Entrance
import day18.vault.Key
import day18.vaultGraph.VaultGraph
import day18.vaultGraph.buildVaultGraph

class VaultExplorerBFS(private val graph: VaultGraph, private val size: Int) : VaultExplorer {
    constructor(textMap: String, size: Int) : this(buildVaultGraph(textMap), size)

    override fun getBestKeyPath(): Pair<List<Key>, Int> {
        val newKeys = graph.getAvailableKeyDistancesFrom(Entrance, emptySet())
        var keyPaths: List<Pair<List<Key>, Int>> = newKeys.map { (key, distance) -> Pair(listOf(key), distance) }

        for (i in 1 until graph.keys.size) {
            keyPaths = keyPaths.flatMap { explorePossibleKeyPaths(it.first, it.second) }
                .sortedBy { it.second + graph.getMaxDistanceToKey(it.first.last(), it.first.toSet()) }
                .take(size)
            println("$i / ${graph.keys.size}")
        }
        return keyPaths.first()
    }

    private fun explorePossibleKeyPaths(keyPath: List<Key>, pathLength: Int): List<Pair<List<Key>, Int>> {
        val newKeys = graph.getAvailableKeyDistancesFrom(keyPath.last(), keyPath.toSet())

        return newKeys.map { (key, distance) ->
            Pair(
                keyPath + key,
                pathLength + distance
            )
        }
    }
}