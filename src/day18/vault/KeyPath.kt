package day18.vault

import day18.vaultGraph.KeyDistance

data class KeyPath(val path: List<Key>, val length: Int) {
    val last = if (path.isEmpty()) Entrance else path.last()
    val size = path.size
    val keys = path.toSet()

    operator fun plus(it: KeyDistance) =
        KeyPath(
            path + it.key,
            length + it.distance
        )
}