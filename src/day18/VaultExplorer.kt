package day18

class VaultExplorer(private val graph: VaultGraph) {
    private val progressPrinter = ProgressPrinter(this)
    var bestPathLength = Int.MAX_VALUE
    private var bestPath: List<Key> = emptyList()
    private val summedDistances: List<Int>

    constructor(textMap: String) : this(VaultGraph(textMap))

    init {
        summedDistances = graph.getKeyNodes()
            .flatMap { it.getDistances() }
            .sorted()
            .subList(0, graph.keys.size)
            .fold(listOf(0)) { sum, distance -> sum + (sum.last() + distance) }
    }

    fun getBestKeyPath(): Pair<List<Key>, Int> {
        bestPathLength = Int.MAX_VALUE
        explorePossibleKeyPaths(mutableListOf(Key('@')), 0)
        return Pair(bestPath, bestPathLength)
    }

    private fun explorePossibleKeyPaths(keyPath: MutableList<Key>, pathLength: Int) {
        if (pathLength >= bestPathLength) {
            progressPrinter.trackProgress("Path")
            return
        }

        if (keyPath.size == graph.keys.size) {
            bestPathLength = pathLength
            bestPath = keyPath.map { it }
            println("\nfound key path of length $bestPathLength : $bestPath")
        }

        if (pathLength + summedDistances[graph.keys.size - keyPath.size] >= bestPathLength) {
            progressPrinter.trackProgress("Min D")
            return
        }

        val maxD = graph.getMaxDistance(keyPath.last(), keyPath)
        if (pathLength + maxD >= bestPathLength) {
            progressPrinter.trackProgress("Max D")
            return
        }

        val newKeys = graph.getAvailableKeysFrom(keyPath.last(), keyPath)

        if (newKeys.map { it.key }.toSet().size != newKeys.size) throw Error("not possible")

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