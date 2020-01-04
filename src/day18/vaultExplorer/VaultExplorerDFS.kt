package day18.vaultExplorer

import day18.vault.Entrance
import day18.vault.Key
import day18.vaultGraph.VaultGraph
import day18.vaultGraph.buildVaultGraph
import java.time.Duration
import java.time.Instant

class VaultExplorerDFS(private val graph: VaultGraph, val maxDuration: Duration) : VaultExplorer {
    private val progressPrinter = ProgressPrinter(this)
    var bestPathLength = Int.MAX_VALUE
    private var bestPath: List<Key> = emptyList()
    var start: Instant = Instant.now()
    private val summedDistances = graph.keys
        .flatMap { graph.getDistancesToKeysFrom(it) }
        .sorted()
        .subList(0, graph.keys.size)
        .fold(listOf(0)) { result, distance -> result + (result.last() + distance) }

    constructor(textMap: String, maxTime: Duration) : this(buildVaultGraph(textMap), maxTime)

    override fun getBestKeyPath(): Pair<List<Key>, Int> {
        bestPathLength = Int.MAX_VALUE
        start = Instant.now()

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

    private fun explorePossibleKeyPaths(
        keyPath: MutableList<Key>,
        pathLength: Int
    ) {
        if (Duration.between(start, Instant.now()) > maxDuration) return

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

        if (pathLength + summedDistances[graph.keys.size - keyPath.size] >= bestPathLength) {
            progressPrinter.trackProgress("Min D")
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