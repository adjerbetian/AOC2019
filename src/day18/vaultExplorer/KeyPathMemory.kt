package day18.vaultExplorer

import day18.vault.KeyPath

internal class KeyPathMemory {
    private val pathMemory = HashMap<String, Int>()

    fun hasSmaller(keyPath: KeyPath) =
        pathMemory.getOrDefault(buildPositionPathHash(keyPath), Int.MAX_VALUE) <= keyPath.length

    fun add(keyPath: KeyPath) {
        pathMemory[buildPositionPathHash(keyPath)] = keyPath.length
    }

    private fun buildPositionPathHash(keyPath: KeyPath) =
        keyPath.last.letter + "." + keyPath.path.sortedBy { it.letter }.joinToString("")
}