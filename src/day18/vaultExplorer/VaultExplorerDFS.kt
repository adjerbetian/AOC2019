package day18.vaultExplorer

import day18.vault.Entrance
import day18.vault.Key
import day18.vaultGraph.VaultGraph
import day18.vaultGraph.buildVaultGraph

class VaultExplorerDFS(private val graph: VaultGraph) : VaultExplorer {
    private var pathMemory = KeyPathMemory()
    private var bestPathLength = Int.MAX_VALUE
    private var bestPath: List<Key> = emptyList()

    constructor(textMap: String) : this(buildVaultGraph(textMap))

    override fun getBestKeyPath(): Pair<List<Key>, Int> {
        bestPathLength = Int.MAX_VALUE
        pathMemory = KeyPathMemory()

        explorePossibleKeyPaths()
        return Pair(bestPath, bestPathLength)
    }

    private fun explorePossibleKeyPaths() {
        val keyPath = mutableListOf<Key>()
        val newKeys = graph.getAvailableKeyDistancesFrom(Entrance, keyPath.toSet())

        newKeys.forEach {
            keyPath.add(it.key)
            explorePossibleKeyPaths(keyPath, it.distance)
            keyPath.removeAt(keyPath.lastIndex)
        }
    }

    private fun explorePossibleKeyPaths(keyPath: MutableList<Key>, pathLength: Int) {
        if (pathMemory[keyPath] <= pathLength) return
        pathMemory[keyPath] = pathLength

        if (pathLength >= bestPathLength) return

        if (keyPath.size == graph.keys.size) {
            bestPathLength = pathLength
            bestPath = keyPath.map { it }
            println("\nfound key path of length $bestPathLength : $bestPath")
        }

        val maxD = graph.getMaxDistanceToKey(keyPath.last(), keyPath.toSet())
        if (pathLength + maxD >= bestPathLength) return

        val newKeys = graph.getAvailableKeyDistancesFrom(keyPath.last(), keyPath.toSet())

        newKeys.forEach {
            keyPath.add(it.key)
            explorePossibleKeyPaths(
                keyPath,
                pathLength + it.distance
            )
            keyPath.removeAt(keyPath.lastIndex)
        }
    }
}

private class KeyPathMemory {
    private val pathMemory = HashMap<String, Int>()

    operator fun get(keyPath: List<Key>) = pathMemory.getOrDefault(buildPositionPathHash(keyPath), Int.MAX_VALUE)
    operator fun set(keyPath: List<Key>, distance: Int) {
        pathMemory[buildPositionPathHash(keyPath)] = distance
    }

    private fun buildPositionPathHash(keyPath: List<Key>) =
        keyPath.last().letter + "." + keyPath.sortedBy { it.letter }.joinToString("")
}
