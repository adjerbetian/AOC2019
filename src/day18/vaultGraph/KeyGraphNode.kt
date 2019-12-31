package day18.vaultGraph

import day18.vault.Door
import day18.vault.Key

data class KeyGraphNode(
    val key: Key,
    val distance: Int = 0,
    val doors: List<Door> = emptyList(),
    val edges: List<KeyGraphNode> = emptyList()
) {
    fun getAccessibleKeyDistances(keys: List<Key>): List<KeyDistance> {
        val result = mutableListOf<KeyDistance>()
        getAccessibleKeyDistances(keys, result)
        return result
    }

    private fun getAccessibleKeyDistances(keys: List<Key>, result: MutableList<KeyDistance>) {
        if (!keys.containsAll(doors.map { it.getKey() })) return

        if (!keys.contains(key)) {
            result.add(KeyDistance(key, distance))
        } else {
            edges.forEach { it.getAccessibleKeyDistances(keys, result) }
        }
    }

    fun getDistances(): List<Int> = edges.flatMap { listOf(it.distance) + it.getDistances() }

    fun getMaxDistance(keys: List<Key>): Int {
        return getAllKeyDistances().filter { !keys.contains(it.key) }.map { it.distance }.max()!!
    }

    private fun getAllKeyDistances(): List<KeyDistance> {
        val result = mutableListOf<KeyDistance>()
        getAllKeyDistances(result)
        return result
    }

    private fun getAllKeyDistances(result: MutableList<KeyDistance>) {
        result.add(KeyDistance(key, distance))
        edges.forEach { it.getAllKeyDistances(result) }
    }

    fun getKeys(): List<Key> = listOf(key) + edges.flatMap { it.getKeys() }
}