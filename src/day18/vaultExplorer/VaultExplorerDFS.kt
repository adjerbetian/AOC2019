package day18.vaultExplorer

import day18.vault.Entrance
import day18.vault.Key
import day18.vaultGraph.VaultGraph
import day18.vaultGraph.buildVaultGraph
import java.time.Instant

class VaultExplorerDFS(private val graph: VaultGraph) : VaultExplorer {
    private val progressPrinter = ProgressPrinter(this)
    var bestPathLength = Int.MAX_VALUE
    private var bestPath: List<Key> = emptyList()
    var start: Instant = Instant.now()
    private var pathMemory = KeyPathMemory()

    constructor(textMap: String) : this(buildVaultGraph(textMap))

    override fun getBestKeyPath(): Pair<List<Key>, Int> {
        bestPathLength = Int.MAX_VALUE
        start = Instant.now()
        pathMemory = KeyPathMemory()

        explorePossibleKeyPaths()
        progressPrinter.printProgress()
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

    private fun explorePossibleKeyPaths(
        keyPath: MutableList<Key>,
        pathLength: Int
    ) {
        if (pathMemory[keyPath] <= pathLength) {
            progressPrinter.trackProgress("KeyCut")
            return
        }
        pathMemory[keyPath] = pathLength

        val keys = keyPath.toSet()

        if (pathLength >= bestPathLength) {
            progressPrinter.trackProgress("Path")
            return
        }

        if (keyPath.size == graph.keys.size) {
            bestPathLength = pathLength
            bestPath = keyPath.map { it }
            println("\nfound key path of length $bestPathLength : $bestPath")
        }

        val maxD = graph.getMaxDistanceToKey(keyPath.last(), keys)
        if (pathLength + maxD >= bestPathLength) {
            progressPrinter.trackProgress("Max D")
            return
        }

        val newKeys = graph.getAvailableKeyDistancesFrom(keyPath.last(), keys)

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
